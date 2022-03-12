package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemEmployeeCardBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EmployeeListAdaptor extends RecyclerView.Adapter<EmployeeListAdaptor.Holder> {

    ArrayList<EmployeeModel> employeeList;

    public EmployeeListAdaptor(ArrayList<EmployeeModel> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEmployeeCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_employee_card, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListAdaptor.Holder holder, int position) {
        EmployeeModel employeeModel =employeeList.get(position);

        holder.binding.tvEmployeeName.setText(employeeModel.getName());
        holder.binding.tvEmployeeSpecialist.setText(employeeModel.getJob());
        Picasso.get().load(employeeModel.getImageUrl()).into(holder.binding.itemEmployeePp);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        ItemEmployeeCardBinding binding;

        public Holder(@NonNull ItemEmployeeCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
