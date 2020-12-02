package com.laundry.laundry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laundry.laundry.R;
import com.laundry.laundry.database.SepatuDAO;

import java.util.ArrayList;
import java.util.List;

public class SepatuRecyclerAdapter extends RecyclerView.Adapter<SepatuRecyclerAdapter.RoomViewHolder> implements Filterable {
    private List<SepatuDAO> dataList;
    private List<SepatuDAO> filteredDataList;
    private Context context;

    public SepatuRecyclerAdapter(Context context, List<SepatuDAO> dataList){
        this.context = context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @NonNull
    @Override
    public SepatuRecyclerAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_adapter_sepatu, parent, false);
        return new SepatuRecyclerAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SepatuRecyclerAdapter.RoomViewHolder holder, int position) {
        final SepatuDAO brg = filteredDataList.get(position);
        holder.tvId.setText(brg.getId());
        holder.tvLayanan.setText(brg.getJenis_layanan());
        holder.tvKondisi.setText(brg.getKondisi());
        holder.tvJenis.setText(brg.getJenis_sepatu());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
//                DetailUserFragment dialog = new DetailUserFragment();
//                dialog.show(manager, "dialog");
//
//                Bundle args = new Bundle();
//                args.putString("id", brg.getId());
//                dialog.setArguments(args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvLayanan, tvKondisi, tvJenis;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdSepatu);
            tvLayanan = itemView.findViewById(R.id.tvLayananSepatu);
            tvKondisi = itemView.findViewById(R.id.tvKondisiSepatu);
            tvJenis = itemView.findViewById(R.id.tvJenisSepatu);
            mParent = itemView.findViewById(R.id.linearLayout);
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
                    List<SepatuDAO> filteredList = new ArrayList<>();
                    for (SepatuDAO SepatuDAO : dataList){
                        if(SepatuDAO.getId().toLowerCase().contains(charSequenceString.toLowerCase())){
                            filteredList.add(SepatuDAO);
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
                filteredDataList = (List<SepatuDAO>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
