package com.laundry.laundry;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.laundry.laundry.database.DatabaseOrder;
import com.laundry.laundry.model.Order;

public class UpdateFragment extends Fragment {

    TextInputEditText edtJumlah, edtBerat, edtLayanan;
    Button saveBtn, deleteBtn, cancelBtn;
    String tempJumlah, tempBerat, layanan;
    Order order;
    Integer jumlah;
    double berat;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        order = (Order) getArguments().getSerializable("order");

        edtJumlah = view.findViewById(R.id.edit_jumlah);
        edtBerat = view.findViewById(R.id.edit_berat);
        edtLayanan = view.findViewById(R.id.edit_layanan);

        cancelBtn = view.findViewById(R.id.btnCancel);
        saveBtn = view.findViewById(R.id.btnSave);
        deleteBtn = view.findViewById(R.id.btnDelete);

        try {
            if((order.getStringId() != null) && (order.getStringJumlah_pakaian() != null) &&
                    (order.getStringBerat() != null) && (order.getLayanan() != null)) {
                edtJumlah.setText(order.getStringJumlah_pakaian());
                edtBerat.setText(order.getStringBerat());
                edtLayanan.setText(order.getLayanan());
            } else {
                edtJumlah.setText("-");
                edtBerat.setText("-");
                edtLayanan.setText("-");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    order.setJumlah_pakaian(jumlah);
                    order.setBerat(berat);
                    order.setLayanan(layanan);
                    update(order);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure want to delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delete(order);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void update(final Order order) {
        class UpdateOrder extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseOrder.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .userDao()
                        .update(order);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(),"Order updated",Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.hide(UpdateFragment.this).commit();
            }
        }
        UpdateOrder updateUser = new UpdateOrder();
        updateUser.execute();
    }

    private void delete(final Order order) {
        class DeletOrder extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseOrder.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .userDao()
                        .delete(order);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "Order deleted", Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.hide(UpdateFragment.this).commit();
            }
        }
    }
}

















