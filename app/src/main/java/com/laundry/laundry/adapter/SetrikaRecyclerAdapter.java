package com.laundry.laundry.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laundry.laundry.R;
import com.laundry.laundry.database.SetrikaDAO;
import com.laundry.laundry.ui.setrika.DetailSetrika;

import java.util.ArrayList;
import java.util.List;

public class SetrikaRecyclerAdapter extends RecyclerView.Adapter<SetrikaRecyclerAdapter.RoomViewHolder> implements Filterable {
    private List<SetrikaDAO> dataList;
    private List<SetrikaDAO> filteredDataList;
    private Context context;

    public SetrikaRecyclerAdapter(Context context, List<SetrikaDAO> dataList){
        this.context = context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_adapter_setrika, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        final SetrikaDAO brg = filteredDataList.get(position);

        holder.tvId.setText(brg.getId());
        holder.tvBerat.setText(String.valueOf(brg.getBerat()));
        holder.tvJumlah.setText(String.valueOf(brg.getJumlah_pakaian()));
        holder.tvJenis.setText(brg.getJenis_pakaian());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                DetailSetrika dialog = new DetailSetrika();
                dialog.show(manager, "dialog");

                Bundle args = new Bundle();
                args.putString("id", brg.getId());
                dialog.setArguments(args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvBerat, tvJumlah, tvJenis;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdSetrika);
            tvBerat = itemView.findViewById(R.id.tvBeratSetrika);
            tvJumlah = itemView.findViewById(R.id.tvJumlahSetrika);
            tvJenis = itemView.findViewById(R.id.tvJenisSetrika);
            mParent = itemView.findViewById(R.id.setrikalayout);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if(charSequenceString.isEmpty()){
                    filteredDataList = dataList;
                } else {
                    List<SetrikaDAO> filteredList = new ArrayList<>();
                    for (SetrikaDAO SetrikaDAO : dataList){
                        if(SetrikaDAO.getId().toLowerCase().contains(charSequenceString.toLowerCase())){
                            filteredList.add(SetrikaDAO);
                        }
                        filteredDataList = filteredList;
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredDataList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredDataList = (List<SetrikaDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
