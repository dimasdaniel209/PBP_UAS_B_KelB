package com.laundry.laundry.ui.setrika;

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
import com.laundry.laundry.api.SetrikaResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSetrika extends Fragment {
    private TextInputEditText edtJumlah, edtBerat;
    private Button cancelBtn, addBtn;
    private String tempJumlah, tempBerat, tempJenis="";
    private int jumlah;
    private double berat;
    private ProgressDialog progressDialog;

    public AddSetrika() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_setrika, container, false);

        progressDialog = new ProgressDialog(getActivity());

        edtJumlah = view.findViewById(R.id.etJumlah);
        edtBerat = view.findViewById(R.id.etBerat);
        cancelBtn = view.findViewById(R.id.btnCancel);
        addBtn = view.findViewById(R.id.btnAdd);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup_jenis);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_biasa:
                        RadioButton radioBiasa = view.findViewById(R.id.radio_biasa);
                        tempJenis = radioBiasa.getText().toString();
                        break;
                    case R.id.radio_suede:
                        RadioButton radioSuede = view.findViewById(R.id.radio_suede);
                        tempJenis = radioSuede.getText().toString();
                        break;
                    case R.id.radio_jersey:
                        RadioButton radioJersey = view.findViewById(R.id.radio_jersey);
                        tempJenis = radioJersey.getText().toString();
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
                fragmentTransaction.hide(AddSetrika.this).commit();
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
                tempJumlah = edtJumlah.getText().toString();
                tempBerat = edtBerat.getText().toString();

                if(tempJumlah.isEmpty()){
                    edtJumlah.setError("Silakan diisi dengan benar");}
                if(tempBerat.isEmpty()){
                    edtBerat.setError("Silakan diisi dengan benar");}
                if (tempJenis.length()==0){
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Silakan pilih jenis pakaian", Toast.LENGTH_SHORT).show();
                }
                if(!tempJumlah.isEmpty() && !tempBerat.isEmpty() && tempJenis.length()>0) {
                    jumlah = Integer.parseInt(tempJumlah);
                    berat = Double.parseDouble(tempBerat);

                    progressDialog.show();

                    addSetrika();

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.anim.slide_in,  // enter
                                    R.anim.fade_out,  // exit
                                    R.anim.fade_in,   // popEnter
                                    R.anim.slide_out // popExit
                            );
                    fragmentTransaction.hide(AddSetrika.this).addToBackStack(null).commit();
                }
            }
        });
    }

    private void addSetrika() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SetrikaResponse> add = apiService.createSetrika(berat, jumlah, tempJenis);

        try {
            add.enqueue(new Callback<SetrikaResponse>() {
                @Override
                public void onResponse(Call<SetrikaResponse> call, Response<SetrikaResponse> response) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.anim.slide_in,  // enter
                                    R.anim.fade_out,  // exit
                                    R.anim.fade_in,   // popEnter
                                    R.anim.slide_out // popExit
                            );
                    fragmentTransaction.hide(AddSetrika.this).commit();
                }

                @Override
                public void onFailure(Call<SetrikaResponse> call, Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }catch (Exception e){
            Toast.makeText(getActivity(), "Terdapat Kesalahan", Toast.LENGTH_SHORT).show();
        }

    }
}