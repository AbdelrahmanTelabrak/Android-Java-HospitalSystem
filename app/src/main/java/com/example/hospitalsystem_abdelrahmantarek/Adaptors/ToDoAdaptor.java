package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemTodoBinding;

import java.util.ArrayList;

public class ToDoAdaptor extends RecyclerView.Adapter<ToDoAdaptor.ToDoHolder> {

    ArrayList<String> list;

    public ToDoAdaptor(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTodoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_todo, parent, false);
        return new ToDoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {
        String item = list.get(position);
        holder.binding.itemTodoTvTodo.setText(item);
        holder.binding.itemTodoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ToDoHolder extends RecyclerView.ViewHolder {
        ItemTodoBinding binding;
        public ToDoHolder(@NonNull ItemTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
