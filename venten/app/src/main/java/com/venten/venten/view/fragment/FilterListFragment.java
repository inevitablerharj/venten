package com.venten.venten.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.venten.venten.R;
import com.venten.venten.view.adapter.FilterListAdapter;
import com.venten.venten.viewmodel.FilterViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterListFragment extends Fragment {

    @BindView(R.id.filter_recycler)
    RecyclerView filter_recycler;

    @BindView(R.id.loadingview)
    ProgressBar loadingView;

    @BindView(R.id.listerror)
    TextView listerror;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshlayout;

    private FilterViewModel filterViewModel;
    private FilterListAdapter filterListAdapter = new FilterListAdapter(new ArrayList<>());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.filter_layout, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filterViewModel = ViewModelProviders.of(this).get(FilterViewModel.class);
        filterViewModel.refresh();

        filter_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        filter_recycler.setAdapter(filterListAdapter);

        refreshlayout.setOnRefreshListener(() -> {
            filter_recycler.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
            listerror.setVisibility(View.GONE);
            filterViewModel.refreshByPassCache();
            refreshlayout.setRefreshing(false);
        });

        observeViewModel();
    }

    private void observeViewModel(){
        filterViewModel.filters.observe(this, filterResponseModels -> {
            if(filterResponseModels != null && filterResponseModels instanceof List){
                filter_recycler.setVisibility(View.VISIBLE);
                filterListAdapter.updateFilterList(filterResponseModels);

            }
        });

        filterViewModel.filterLoadError.observe(this, isError -> {
            if(isError != null && isError instanceof Boolean){
                listerror.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        filterViewModel.loading.observe(this, aBoolean -> {
            if(aBoolean != null && aBoolean instanceof Boolean){
                loadingView.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                if(aBoolean){
                    filter_recycler.setVisibility(View.GONE);
                    listerror.setVisibility(View.GONE);
                }
            }
        });
    }
}
