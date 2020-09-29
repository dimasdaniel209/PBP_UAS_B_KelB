package com.laundry.laundry;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.laundry.laundry.database.DatabaseOrder;
import com.laundry.laundry.model.Order;

public class AddFragment extends Fragment {

    TextInputEditText edtJumlah, edtBerat, edtLayanan;
    Button cancelBtn, addBtn;

    String tempJumlah, tempBerat, layanan;
    int jumlah;
    double berat;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        edtJumlah = view.findViewById(R.id.add_jumlah);
        edtBerat = view.findViewById(R.id.add_berat);
        edtLayanan = view.findViewById(R.id.add_layanan);
        cancelBtn = view.findViewById(R.id.btnCancel);
        addBtn = view.findViewById(R.id.btnAdd);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(AddFragment.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempJumlah = edtJumlah.getText().toString();
                jumlah = Integer.parseInt(tempJumlah);
                tempBerat = edtBerat.getText().toString();
                berat = Double.parseDouble(tempBerat);
                layanan = edtLayanan.getText().toString();

                if(tempJumlah.isEmpty()){
                    edtJumlah.setError("Please fill correctly");}
                if(tempBerat.isEmpty()){
                    edtBerat.setError("Please fill correctly");}
                if(layanan.isEmpty()){
                    edtLayanan.setError("Please fill correctly");}
                if(!tempJumlah.isEmpty() && !tempBerat.isEmpty() && !layanan.isEmpty()) {
                    addUser();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.hide(AddFragment.this).commit();
                }
            }
        });
    }

    private void addUser(){
        class AddUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                    Order order = new Order();
                    order.setBerat(berat);
                    order.setJumlah_pakaian(jumlah);
                    order.setLayanan(layanan);

                    DatabaseOrder.getInstance(getContext())
                            .getDatabase()
                            .userDao()
                            .insert(order);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                    Toast.makeText(getActivity().getApplicationContext(), "Order saved", Toast.LENGTH_SHORT).show();
            }
        }
        AddUser add = new AddUser();
        add.execute();
    }
}