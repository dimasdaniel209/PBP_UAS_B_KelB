package com.laundry.laundry.ui.setrika;

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
import com.laundry.laundry.api.SetrikaResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSetrika extends DialogFragment {
    private TextView tvId, tvJumlah, tvBerat, tvJenis;
    private String sIdSetrika, sJumlah, sBerat, sJenis;
    private ImageButton ibClose;
    private ProgressDialog progressDialog;
    private MaterialButton btnDelete, btnUpdate;

    public static DetailSetrika newInstance(){ return new DetailSetrika(); }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_setrika, container, false);

        sIdSetrika = getArguments().getString("id", "");

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
                                Call<SetrikaResponse> delete = apiService.deleteSetrika(sIdSetrika,"data");

                                delete.enqueue(new Callback<SetrikaResponse>() {
                                    @Override
                                    public void onResponse(Call<SetrikaResponse> call, Response<SetrikaResponse> response) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.hide(DetailSetrika.this).commit();
                                    }

                                    @Override
                                    public void onFailure(Call<SetrikaResponse> call, Throwable t) {
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

        tvId = view.findViewById(R.id.tvIdSetrika);
        tvJumlah = view.findViewById(R.id.tvJumlahSetrika);
        tvBerat = view.findViewById(R.id.tvBeratSetrika);
        tvJenis = view.findViewById(R.id.tvJenisSetrika);

        loadSetrikaById(sIdSetrika);

        return view;
    }

    private void loadSetrikaById(String id) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SetrikaResponse> add = apiService.getSetrikaById(id, "data");

        add.enqueue(new Callback<SetrikaResponse>() {
            @Override
            public void onResponse(Call<SetrikaResponse> call, Response<SetrikaResponse> response) {
                sIdSetrika = response.body().getSetrikas().get(0).getId();
                sJumlah = String.valueOf(response.body().getSetrikas().get(0).getJumlah_pakaian());
                sBerat = String.valueOf(response.body().getSetrikas().get(0).getBerat());
                sJenis = response.body().getSetrikas().get(0).getJenis_pakaian();

                tvId.setText(sIdSetrika);
                tvJumlah.setText(sJumlah);
                tvBerat.setText(sBerat);
                tvJenis.setText(sJenis);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SetrikaResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}