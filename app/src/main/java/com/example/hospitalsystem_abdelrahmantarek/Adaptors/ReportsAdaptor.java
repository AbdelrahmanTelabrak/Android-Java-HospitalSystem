package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.ReportCardData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.Reports.ReportsListsFragmentDirections;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.MenusViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemReportBinding;

import java.util.ArrayList;

public class ReportsAdaptor extends RecyclerView.Adapter<ReportsAdaptor.ReportCardViewHolder> {
    ArrayList<ReportCardData> reportsList;
    NavController navController;

    public ReportsAdaptor(ArrayList<ReportCardData> reportsList) {
        this.reportsList = reportsList;
    }

    @NonNull
    @Override
    public ReportCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReportBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_report, parent, false);
        return new ReportCardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportCardViewHolder holder, int position) {
        ReportCardData data = reportsList.get(position);
        holder.binding.itemReportCardRName.setText(data.getReportName());
        holder.binding.itemReportCardDate.setText(data.getCreatedAt());
        if(data.getStatus().equals("pending"))
            holder.binding.ivItemRCardStatus.setImageResource(R.drawable.iv_status_process);
        else
            holder.binding.ivItemRCardStatus.setImageResource(R.drawable.iv_status_finished);

        holder.binding.cardViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(view);
                ReportsListsFragmentDirections.ActionReportsListsFragmentToReportDetailsFragment action =
                        ReportsListsFragmentDirections.actionReportsListsFragmentToReportDetailsFragment(data.getId());
                navController.navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportsList.size();
    }

    class ReportCardViewHolder extends RecyclerView.ViewHolder {
        ItemReportBinding binding;
        public ReportCardViewHolder(@NonNull ItemReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
