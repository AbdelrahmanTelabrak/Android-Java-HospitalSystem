package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.ToDo;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemCheckTodoBinding;

import java.util.ArrayList;

public class CheckToDoAdapter extends RecyclerView.Adapter<CheckToDoAdapter.CheckToDoHolder> {
    ArrayList<ToDo> list;
    String taskStatus;
    int checked = 0;
    String empType;
    private static final String TAG = "CheckToDoAdapter";

    public CheckToDoAdapter(ArrayList<ToDo> list, String taskStatus, String empType) {
        this.list = list;
        this.taskStatus = taskStatus;
        this.empType = empType;
    }

    @NonNull
    @Override
    public CheckToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCheckTodoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_check_todo, parent, false);
        return new CheckToDoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckToDoHolder holder, int position) {
        ToDo toDo = list.get(position);
        if (taskStatus.equals("done")){
            holder.binding.ICTCheckBox.setChecked(true);
            holder.binding.ICTCheckBox.setEnabled(false);
        }

        if (empType.toLowerCase().equals("manger"))
            holder.binding.ICTCheckBox.setEnabled(false);

        holder.binding.ICTCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    checked+=1;
                else
                    checked-=1;
                Log.i(TAG, "onCheckedChanged: checked = "+ checked);
            }
        });
        holder.binding.tvICTTodo.setText(toDo.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int checkedCount(){
        return checked;
    }

    class CheckToDoHolder extends RecyclerView.ViewHolder {
        ItemCheckTodoBinding binding;
        public CheckToDoHolder(@NonNull ItemCheckTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
