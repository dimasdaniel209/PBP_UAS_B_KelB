package com.laundry.laundry.ui.setrika;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.laundry.laundry.R;
import com.laundry.laundry.adapter.SetrikaRecyclerAdapter;
import com.laundry.laundry.api.ApiClient;
import com.laundry.laundry.api.ApiInterface;
import com.laundry.laundry.api.SetrikaResponse;
import com.laundry.laundry.database.SetrikaDAO;
import com.laundry.laundry.ui.home.AddFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetrikaFragment extends Fragment {
    private RecyclerView recyclerView;
    private SetrikaRecyclerAdapter recyclerAdapter;
    private List<SetrikaDAO> setrika = new ArrayList<>();
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefresh;
    private FloatingActionButton fabAdd;

    public SetrikaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setrika, container, false);

        searchView = view.findViewById(R.id.search_view);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        fabAdd = view.findViewById(R.id.fab);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new AddSetrika()).addToBackStack(null).commit();
            }
        });

        swipeRefresh.setRefreshing(true);
        loadSetrika(view);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadSetrika(view);
            }
        });
        return view;
    }

    private void loadSetrika(View view) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SetrikaResponse> call = apiService.getAllSetrika("data");

        call.enqueue(new Callback<SetrikaResponse>() {
            @Override
            public void onResponse(Call<SetrikaResponse> call, Response<SetrikaResponse> response) {
                generateDataList(response.body().getSetrikas(), view);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<SetrikaResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void generateDataList(List<SetrikaDAO> setrikaList, View view) {
        recyclerView = view.findViewById(R.id.setrika_rv);
        recyclerAdapter = new SetrikaRecyclerAdapter(getActivity().getApplicationContext(), setrikaList);
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