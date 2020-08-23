package com.venten.venten.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.venten.venten.R;
import com.venten.venten.databinding.FilterLayoutItemsBinding;
import com.venten.venten.model.data.FilterResponseModel;
import com.venten.venten.view.fragment.FilterListFragmentDirections;
import com.venten.venten.view.interfaces.FilterClickListener;

import java.util.ArrayList;
import java.util.List;

public class FilterListAdapter extends RecyclerView.Adapter<FilterListAdapter.FilterViewHolder> implements FilterClickListener {

    private ArrayList<FilterResponseModel> filterResponseModelArrayList;

    public FilterListAdapter(ArrayList<FilterResponseModel> filterResponseModels){
        this.filterResponseModelArrayList = filterResponseModels;
    }

    public void updateFilterList(List<FilterResponseModel> filterResponseModelList){
        filterResponseModelArrayList.clear();
        filterResponseModelArrayList.addAll(filterResponseModelList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FilterLayoutItemsBinding view = DataBindingUtil.inflate(layoutInflater, R.layout.filter_layout_items,parent,false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        holder.itemView.setFilter(filterResponseModelArrayList.get(position));
        holder.itemView.setListener(this);
    }

    @Override
    public int getItemCount() {
        return filterResponseModelArrayList.size();
    }

    @Override
    public void onFilterClicked(View v) {
        String idString = ((TextView)v.findViewById(R.id.filterId)).getText().toString();
        int id = Integer.valueOf(idString);
        FilterListFragmentDirections.ActionCarList actionCarList = FilterListFragmentDirections.actionCarList();
        actionCarList.setFilterId(id);
        Navigation.findNavController(v).navigate(actionCarList);

    }

    class FilterViewHolder extends RecyclerView.ViewHolder{

        public FilterLayoutItemsBinding itemView;

        public FilterViewHolder(@NonNull FilterLayoutItemsBinding itemFilterBinding) {
            super(itemFilterBinding.getRoot());
            this.itemView = itemFilterBinding;
        }
    }
}
