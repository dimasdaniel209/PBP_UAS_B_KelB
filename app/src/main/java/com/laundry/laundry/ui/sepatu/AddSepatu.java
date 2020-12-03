package com.laundry.laundry.ui.sepatu;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.laundry.laundry.R;
import com.laundry.laundry.api.ApiClient;
import com.laundry.laundry.api.ApiInterface;
import com.laundry.laundry.api.SepatuResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSepatu extends Fragment {
    private TextInputEditText edtJenisSepatu, edtKondisi;
    private Button cancelBtn, addBtn;
    private String tempJenisSepatu, tempKondisi, tempJenisLayanan="";
    private ProgressDialog progressDialog;

    public AddSepatu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_sepatu, container, false);

        progressDialog = new ProgressDialog(getActivity());

        edtKondisi = view.findViewById(R.id.etKondisiSepatu);
        edtJenisSepatu = view.findViewById(R.id.etJenisSepatu);
        cancelBtn = view.findViewById(R.id.btnCancel);
        addBtn = view.findViewById(R.id.btnAdd);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup_jenis);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_biasa:
                        RadioButton radioBiasa = view.findViewById(R.id.radio_biasa);
                        tempJenisLayanan = radioBiasa.getText().toString();
                        break;
                    case R.id.radio_kilat:
                        RadioButton radioKilat = view.findViewById(R.id.radio_kilat);
                        tempJenisLayanan = radioKilat.getText().toString();
                        break;
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in,  // enter
                                R.anim.fade_out,  // exit
                                R.anim.fade_in,   // popEnter
                                R.anim.slide_out // popExit
                        );
                fragmentTransaction.hide(AddSepatu.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempKondisi = edtKondisi.getText().toString();
                tempJenisSepatu = edtJenisSepatu.getText().toString();

                if(tempKondisi.isEmpty()){
                    edtKondisi.setError("Silakan diisi dengan benar");}
                if(tempJenisSepatu.isEmpty()){
                    edtJenisSepatu.setError("Silakan diisi dengan benar");}
                if (tempJenisLayanan.length()==0){
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Silakan pilih jenis layanan", Toast.LENGTH_SHORT).show();
                }
                if(!tempKondisi.isEmpty() && !tempJenisSepatu.isEmpty() && tempJenisLayanan.length()>0) {

                    progressDialog.show();

                    addSepatu();

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.anim.slide_in,  // enter
                                    R.anim.fade_out,  // exit
                                    R.anim.fade_in,   // popEnter
                                    R.anim.slide_out // popExit
                            );
                    fragmentTransaction.hide(AddSepatu.this).addToBackStack(null).commit();
                }
            }
        });
    }

    private void addSepatu() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SepatuResponse> add = apiService.createSepatu(tempJenisLayanan, tempKondisi, tempJenisSepatu);

        try {
            add.enqueue(new Callback<SepatuResponse>() {
                @Override
                public void onResponse(Call<SepatuResponse> call, Response<SepatuResponse> response) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.anim.slide_in,  // enter
                                    R.anim.fade_out,  // exit
                                    R.anim.fade_in,   // popEnter
                                    R.anim.slide_out // popExit
                            );
                    fragmentTransaction.hide(AddSepatu.this).commit();
                }

                @Override
                public void onFailure(Call<SepatuResponse> call, Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(), "Terdapat Kesalahan", Toast.LENGTH_SHORT).show();
        }

    }

}