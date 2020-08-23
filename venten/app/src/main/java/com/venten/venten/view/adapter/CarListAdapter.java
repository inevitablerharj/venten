package com.venten.venten.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.venten.venten.R;
import com.venten.venten.databinding.CarLayoutItemsBinding;
import com.venten.venten.model.data.CarDataModel;

import java.util.ArrayList;
import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarViewHolder> {

    private ArrayList<CarDataModel> carDataModelArrayList;

    public CarListAdapter(ArrayList<CarDataModel> carDataModelArrayList){
        this.carDataModelArrayList = carDataModelArrayList;
    }

    public void updateCarList(List<CarDataModel> carDataModelList){
        carDataModelArrayList.clear();
        carDataModelArrayList.addAll(carDataModelList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarListAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         CarLayoutItemsBinding view = DataBindingUtil.inflate(layoutInflater, R.layout.car_layout_items,parent,false);
         return new CarListAdapter.CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarListAdapter.CarViewHolder holder, int position) {
        holder.itemView.setCar(carDataModelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return carDataModelArrayList.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder{

        public CarLayoutItemsBinding itemView;

        public CarViewHolder(@NonNull CarLayoutItemsBinding itemFilterBinding) {
            super(itemFilterBinding.getRoot());
            this.itemView = itemFilterBinding;
        }
    }
}
