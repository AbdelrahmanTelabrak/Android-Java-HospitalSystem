package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.NurseReply;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemNurseReplyBinding;

import java.util.ArrayList;

public class NurseReplyAdaptor extends RecyclerView.Adapter<NurseReplyAdaptor.NurseReplyHolder> {
    ArrayList<NurseReply> replies;

    public NurseReplyAdaptor(ArrayList<NurseReply> replies) {
        this.replies = replies;
    }

    @NonNull
    @Override
    public NurseReplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNurseReplyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_nurse_reply, parent, false);
        return new NurseReplyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NurseReplyHolder holder, int position) {
        NurseReply reply = replies.get(position);
        if(reply.getBloodPressure() != null)
            holder.binding.tvINRBloodPressure.setText(reply.getBloodPressure());
        if(reply.getSugarAnalysis()!= null)
            holder.binding.tvINRSugarAnalysis.setText(reply.getSugarAnalysis());
        if(reply.getTemperature() != null)
            holder.binding.tvINRTemperature.setText(reply.getTemperature());
        if(reply.getFluidBalance() != null)
            holder.binding.tvINRFluidBalance.setText(reply.getFluidBalance());
        if(reply.getRespiratoryRate() != null)
            holder.binding.tvINRRespiratoryRate.setText(reply.getRespiratoryRate());
        if(reply.getHeartRate() != null)
            holder.binding.tvINRHeartRate.setText(reply.getHeartRate());
        holder.binding.tvINRReplyDescription.setText(reply.getMeasurementNote());
        holder.binding.tvINRNurseName.setText(reply.getNurseFullName());
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    class NurseReplyHolder extends RecyclerView.ViewHolder {
        ItemNurseReplyBinding binding;
        public NurseReplyHolder(@NonNull ItemNurseReplyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
