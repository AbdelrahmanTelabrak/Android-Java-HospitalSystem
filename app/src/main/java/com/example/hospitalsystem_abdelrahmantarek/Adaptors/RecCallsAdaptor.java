package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemCallsCardBinding;

import java.util.ArrayList;

public class RecCallsAdaptor extends RecyclerView.Adapter<RecCallsAdaptor.Holder> {

    ArrayList<CallData> callsList;

    public RecCallsAdaptor(ArrayList<CallData> callsList) {
        this.callsList = callsList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCallsCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_calls_card, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CallData callData = callsList.get(position);
        holder.binding.itemTvCaseName.setText(callData.getPatientName());
        holder.binding.itemTvCallDate.setText(callData.getCreatedAt());

        if(callData.getStatus().equals("accept_doctor")){
            holder.binding.itemCallsIvStatus.setImageResource(R.drawable.ic_completed);
        }else{
            holder.binding.itemCallsIvStatus.setImageResource(R.drawable.ic_busy);
        }
    }

    @Override
    public int getItemCount() {
        return callsList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ItemCallsCardBinding binding;
        public Holder(@NonNull ItemCallsCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
