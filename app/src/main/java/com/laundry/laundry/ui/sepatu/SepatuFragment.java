package com.laundry.laundry.ui.sepatu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.laundry.laundry.R;
import com.laundry.laundry.adapter.SepatuRecyclerAdapter;
import com.laundry.laundry.api.ApiClient;
import com.laundry.laundry.api.ApiInterface;
import com.laundry.laundry.api.SepatuResponse;
import com.laundry.laundry.database.SepatuDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SepatuFragment extends Fragment {
    private RecyclerView recyclerView;
    private SepatuRecyclerAdapter recyclerAdapter;
    private List<SepatuDAO> sepatu = new ArrayList<>();
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefresh;

    public SepatuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sepatu, container, false);

        searchView = view.findViewById(R.id.search_view);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);

        swipeRefresh.setRefreshing(true);
        loadSepatu(view);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadSepatu(view);
            }
        });
        return view;
    }

    private void loadSepatu(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SepatuResponse> call = apiService.getAllSepatu("data");

        call.enqueue(new Callback<SepatuResponse>() {
            @Override
            public void onResponse(Call<SepatuResponse> call, Response<SepatuResponse> response) {
                generateDataList(response.body().getSepatus(), view);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<SepatuResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void generateDataList(List<SepatuDAO> sepatuList, View view) {
        recyclerView = view.findViewById(R.id.sepatu_rv);
        recyclerAdapter = new SepatuRecyclerAdapter(getActivity().getApplicationContext(), sepatuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                recyclerAdapter.getFilter().filter(queryString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                recyclerAdapter.getFilter().filter(queryString);
                return false;
            }
        });
    }
}