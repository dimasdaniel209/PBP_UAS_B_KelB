package com.laundry.laundry.ui.sepatu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.laundry.laundry.R;
import com.laundry.laundry.api.ApiClient;
import com.laundry.laundry.api.ApiInterface;
import com.laundry.laundry.api.SepatuResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSepatu extends DialogFragment {
    private TextView tvId, tvJenisLayanan, tvKondisi, tvJenisSepatu;
    private String sIdSepatu, sJenisLayanan, sKondisi, sJenisSepatu;
    private ImageButton ibClose;
    private ProgressDialog progressDialog;
    private MaterialButton btnDelete, btnUpdate;

    public static DetailSepatu newInstance(){ return new DetailSepatu(); }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_sepatu, container, false);

        sIdSepatu = getArguments().getString("id", "");

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        ibClose = view.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure want to delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<SepatuResponse> delete = apiService.deleteSepatu(sIdSepatu,"data");

                                delete.enqueue(new Callback<SepatuResponse>() {
                                    @Override
                                    public void onResponse(Call<SepatuResponse> call, Response<SepatuResponse> response) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.hide(DetailSepatu.this).commit();
                                    }

                                    @Override
                                    public void onFailure(Call<SepatuResponse> call, Throwable t) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        tvId = view.findViewById(R.id.tvIdSepatu);
        tvJenisLayanan = view.findViewById(R.id.tvJenisSepatu);
        tvKondisi = view.findViewById(R.id.tvKondisiSepatu);
        tvJenisSepatu = view.findViewById(R.id.tvJenisSepatu);

        loadSepatuById(sIdSepatu);

        return view;
    }

    private void loadSepatuById(String id) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SepatuResponse> add = apiService.getSepatuById(id, "data");

        add.enqueue(new Callback<SepatuResponse>() {
            @Override
            public void onResponse(Call<SepatuResponse> call, Response<SepatuResponse> response) {
                sIdSepatu= response.body().getSepatus().get(0).getId();
                sJenisLayanan = response.body().getSepatus().get(0).getJenis_layanan();
                sKondisi = response.body().getSepatus().get(0).getKondisi();
                sJenisSepatu = response.body().getSepatus().get(0).getJenis_sepatu();

                tvId.setText(sIdSepatu);
                tvJenisLayanan.setText(sJenisLayanan);
                tvKondisi.setText(sKondisi);
                tvJenisSepatu.setText(sJenisSepatu);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SepatuResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}