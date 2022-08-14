package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.ReportDetailsData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemManagerReplyBinding;

import java.util.ArrayList;

public class ManagerReplyAdaptor extends RecyclerView.Adapter<ManagerReplyAdaptor.ReplyHolder> {
    ArrayList<ReportDetailsData> reportReplys;

    public ManagerReplyAdaptor(ArrayList<ReportDetailsData> reportDetails) {
        this.reportReplys = reportDetails;
    }

    @NonNull
    @Override
    public ReplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemManagerReplyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_manager_reply, parent, false);
        return new ReplyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyHolder holder, int position) {
        ReportDetailsData reportDetailsData = reportReplys.get(position);
        String managerFullName = reportDetailsData.getManger().getFirstName()+" "+reportDetailsData.getManger().getLastName();
        holder.binding.tvIMRMangerName.setText(managerFullName);
        holder.binding.tvIMRReplyDate.setText(reportDetailsData.getManger().getUpdatedAt());
        holder.binding.tvIMRReplyDescription.setText(reportDetailsData.getAnswer());
    }

    @Override
    public int getItemCount() {
        return reportReplys.size();
    }


    class ReplyHolder extends RecyclerView.ViewHolder {
        ItemManagerReplyBinding binding;
        public ReplyHolder(@NonNull ItemManagerReplyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
