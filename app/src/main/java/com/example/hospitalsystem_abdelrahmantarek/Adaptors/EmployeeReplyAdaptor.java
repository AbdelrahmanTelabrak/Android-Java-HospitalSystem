package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.TaskDetails;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemManagerReplyBinding;

import java.util.ArrayList;

public class EmployeeReplyAdaptor extends RecyclerView.Adapter<EmployeeReplyAdaptor.EmpReplyHolder> {

    ArrayList<TaskDetails> empReply;

    public EmployeeReplyAdaptor(ArrayList<TaskDetails> empReply) {
        this.empReply = empReply;
    }

    @NonNull
    @Override
    public EmpReplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemManagerReplyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_manager_reply, parent, false);
        return new EmpReplyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpReplyHolder holder, int position) {
        TaskDetails taskDetails = empReply.get(position);
        String specialist, fullName, type;
        type = taskDetails.getUser().getSpecialist();
        specialist = "Specialist, " + type;
        fullName = taskDetails.getUser().getFirstName() + " " + taskDetails.getUser().getLastName();
        holder.binding.tvIMRSpecialist.setText(specialist);
        holder.binding.tvIMRMangerName.setText(fullName);
        holder.binding.tvIMRReplyDescription.setText(taskDetails.getNote());
        if(type.trim().equalsIgnoreCase("doctor")){
            holder.binding.imageView12.setImageResource(R.drawable.iv_dpp_doctor);
        }
        else if(type.trim().equalsIgnoreCase("nurse")){
            holder.binding.imageView12.setImageResource(R.drawable.iv_dpp_nurse);
        }
        else if(type.trim().equalsIgnoreCase("receptionist")){
            holder.binding.imageView12.setImageResource(R.drawable.iv_dpp_receptionist);
        }
        else if(type.trim().equalsIgnoreCase("analysis")){
            holder.binding.imageView12.setImageResource(R.drawable.iv_dpp_analysis);
        }
        else if(type.trim().equalsIgnoreCase("hr")){
            holder.binding.imageView12.setImageResource(R.drawable.iv_dpp_hr);
        }
        else {
            holder.binding.imageView12.setImageResource(R.drawable.iv_dpp_manager);
        }
    }

    @Override
    public int getItemCount() {
        return empReply.size();
    }

    class EmpReplyHolder extends RecyclerView.ViewHolder {
        ItemManagerReplyBinding binding;
        public EmpReplyHolder(@NonNull ItemManagerReplyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
