package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemEmployeeCardBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class EmployeeListAdaptor extends RecyclerView.Adapter<EmployeeListAdaptor.Holder> implements Filterable {

    ArrayList<DNAData> employeeList;
    ArrayList<DNAData> allEmployeeList;

    public EmployeeListAdaptor(ArrayList<DNAData> employeeList) {
        this.employeeList = employeeList;
        allEmployeeList = new ArrayList<>(employeeList);
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
        DNAData employeeModel =employeeList.get(position);

        if(employeeModel.getType().trim().equalsIgnoreCase("doctor")){
            holder.binding.tvEmployeeName.setText("Dr. "+employeeModel.getFirstName());
            holder.binding.itemEmployeePp.setImageResource(R.drawable.iv_dpp_doctor);
        }
        else if(employeeModel.getType().trim().equalsIgnoreCase("nurse")){
            holder.binding.tvEmployeeName.setText(employeeModel.getFirstName());
            holder.binding.itemEmployeePp.setImageResource(R.drawable.iv_dpp_nurse);
        }
        else if(employeeModel.getType().trim().equalsIgnoreCase("receptionist")){
            holder.binding.tvEmployeeName.setText(employeeModel.getFirstName());
            holder.binding.itemEmployeePp.setImageResource(R.drawable.iv_dpp_receptionist);
        }
        else if(employeeModel.getType().trim().equalsIgnoreCase("analysis")){
            holder.binding.tvEmployeeName.setText(employeeModel.getFirstName());
            holder.binding.itemEmployeePp.setImageResource(R.drawable.iv_dpp_analysis);
        }
        else if(employeeModel.getType().trim().equalsIgnoreCase("hr")){
            holder.binding.tvEmployeeName.setText(employeeModel.getFirstName());
            holder.binding.itemEmployeePp.setImageResource(R.drawable.iv_dpp_hr);
        }
        else {
            holder.binding.tvEmployeeName.setText("Mr. "+employeeModel.getFirstName());
            holder.binding.itemEmployeePp.setImageResource(R.drawable.iv_dpp_manager);
        }
        holder.binding.tvEmployeeSpecialist.setText(employeeModel.getType());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<DNAData> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(allEmployeeList);
            }
            else{
                for(DNAData doctorData : allEmployeeList){
                    if(doctorData.getFirstName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(doctorData);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            employeeList.clear();
            employeeList.addAll((Collection<? extends DNAData>) results.values);
            notifyDataSetChanged();
        }
    };

    class Holder extends RecyclerView.ViewHolder{

        ItemEmployeeCardBinding binding;

        public Holder(@NonNull ItemEmployeeCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
