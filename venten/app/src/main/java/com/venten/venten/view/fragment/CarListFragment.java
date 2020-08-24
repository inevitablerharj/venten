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

import com.venten.venten.R;
import com.venten.venten.view.adapter.CarListAdapter;
import com.venten.venten.viewmodel.CarViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarListFragment extends Fragment {

    @BindView(R.id.filter_recycler)
    RecyclerView filter_recycler;

    @BindView(R.id.loadingview)
    ProgressBar loadingView;

    @BindView(R.id.listerror)
    TextView listerror;

    int filterId;
    private CarViewModel carViewModel;
    private CarListAdapter carListAdapter = new CarListAdapter(new ArrayList<>());


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            filterId = CarListFragmentArgs.fromBundle(getArguments()).getFilterId();
            carViewModel = ViewModelProviders.of(this).get(CarViewModel.class);
            carViewModel.fetchFilterObject(filterId);
        }

        filter_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        filter_recycler.setAdapter(carListAdapter);

        observeViewModel();
    }

    private void observeViewModel() {

        carViewModel.mutableLiveData.observe(getViewLifecycleOwner(),carViewModel ->{
            if(carViewModel !=null && carViewModel instanceof List){
                filter_recycler.setVisibility(View.VISIBLE);
                carListAdapter.updateCarList(carViewModel);

            }
        });

        carViewModel.filterLoadError.observe(getViewLifecycleOwner(), isError ->{
            if(isError != null && isError instanceof Boolean){
                listerror.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        carViewModel.loading.observe(getViewLifecycleOwner(), aBoolean ->{
            if(aBoolean != null && aBoolean instanceof Boolean){
                loadingView.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                if(aBoolean){
                    filter_recycler.setVisibility(View.GONE);
                    listerror.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.car_layout, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


}
