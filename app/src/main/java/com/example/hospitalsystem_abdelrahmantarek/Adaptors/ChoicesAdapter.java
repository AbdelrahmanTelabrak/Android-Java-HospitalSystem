package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemChoicesBinding;

import java.util.ArrayList;

public class ChoicesAdapter extends RecyclerView.Adapter<ChoicesAdapter.ChoicesHolder> {
    ArrayList<String> choices;

    public ChoicesAdapter(ArrayList<String> choices) {
        this.choices = choices;
    }

    @NonNull
    @Override
    public ChoicesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChoicesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_choices, parent, false);
        return new ChoicesHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoicesHolder holder, int position) {
        String  choice = choices.get(position);
        holder.binding.tvIChoiceChoice.setText(choice);
        holder.binding.ibIChoicesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choices.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return choices.size();
    }

    class ChoicesHolder extends RecyclerView.ViewHolder {
        ItemChoicesBinding binding;
        public ChoicesHolder(@NonNull ItemChoicesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
