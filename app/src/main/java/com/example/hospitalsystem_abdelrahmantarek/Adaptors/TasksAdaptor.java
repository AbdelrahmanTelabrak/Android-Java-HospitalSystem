package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.TaskData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemTaskBinding;

import java.util.ArrayList;

public class TasksAdaptor extends RecyclerView.Adapter<TasksAdaptor.TasksHolder> {

    private ArrayList<TaskData> tasksList;

    public TasksAdaptor(ArrayList<TaskData> tasksList) {
        this.tasksList = tasksList;
    }

    @NonNull
    @Override
    public TasksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_task, parent, false);
        return new TasksHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksHolder holder, int position) {
        TaskData data = tasksList.get(position);

        holder.binding.itemTaskTName.setText(data.getTaskName());
        holder.binding.itemTaskTvDate.setText(data.getCreatedAt());
        if(data.getStatus().equals("pending"))
            holder.binding.ivItemTStatus.setImageResource(R.drawable.iv_status_process);
        else
            holder.binding.ivItemTStatus.setImageResource(R.drawable.iv_status_finished);

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    class TasksHolder extends RecyclerView.ViewHolder {
        ItemTaskBinding binding;
        public TasksHolder(@NonNull ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
