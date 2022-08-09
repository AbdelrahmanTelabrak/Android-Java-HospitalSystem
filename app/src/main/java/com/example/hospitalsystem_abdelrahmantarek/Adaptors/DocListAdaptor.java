package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemCallsCardBinding;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemDoctorCardBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class DocListAdaptor extends RecyclerView.Adapter<DocListAdaptor.Holder> implements Filterable {

    private ArrayList<DNAData> doctorsList;
    private ArrayList<DNAData> allDoctorsList;
    private int checkedPosition = -1;
    private ItemClickListener itemClickListener;

    public DocListAdaptor(ArrayList<DNAData> doctorsList, ItemClickListener itemClickListener) {
        this.doctorsList = doctorsList;
        this.itemClickListener = itemClickListener;
        this.allDoctorsList = new ArrayList<>(doctorsList);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_doctor_card, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DNAData employeeModel = doctorsList.get(position);
        if(employeeModel.getType().trim().equalsIgnoreCase("doctor")){
            holder.binding.itemDocTvName.setText("Dr. "+employeeModel.getFirstName());
            holder.binding.itemDocPp.setImageResource(R.drawable.iv_dpp_doctor);
        }
        else if(employeeModel.getType().trim().equalsIgnoreCase("nurse")){
            holder.binding.itemDocTvName.setText(employeeModel.getFirstName());
            holder.binding.itemDocPp.setImageResource(R.drawable.iv_dpp_nurse);
        }
        else if(employeeModel.getType().trim().equalsIgnoreCase("receptionist")){
            holder.binding.itemDocTvName.setText(employeeModel.getFirstName());
            holder.binding.itemDocPp.setImageResource(R.drawable.iv_dpp_receptionist);
        }
        else if(employeeModel.getType().trim().equalsIgnoreCase("analysis")){
            holder.binding.itemDocTvName.setText(employeeModel.getFirstName());
            holder.binding.itemDocPp.setImageResource(R.drawable.iv_dpp_analysis);
        }
        else if(employeeModel.getType().trim().equalsIgnoreCase("hr")){
            holder.binding.itemDocTvName.setText(employeeModel.getFirstName());
            holder.binding.itemDocPp.setImageResource(R.drawable.iv_dpp_hr);
        }
        else {
            holder.binding.itemDocTvName.setText("Mr. "+employeeModel.getFirstName());
            holder.binding.itemDocPp.setImageResource(R.drawable.iv_dpp_manager);
        }
        holder.binding.itemDocTvSpecialist.setText("Specialist - "+employeeModel.getType());

        holder.binding.itemDocRb.setChecked(position == checkedPosition);
//        holder.binding.itemDocRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    checkedPosition = holder.getAdapterPosition();
//                    itemClickListener.onClick(doctorsList.get(checkedPosition).getFirstName(), doctorData.getId());
//                }
//            }
//        });

        holder.binding.itemDocRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedPosition = holder.getAdapterPosition();
                itemClickListener.onClick(doctorsList.get(checkedPosition).getFirstName(), doctorsList.get(checkedPosition).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
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
                filteredList.addAll(allDoctorsList);
            }
            else{
                for(DNAData doctorData : allDoctorsList){
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
            doctorsList.clear();
            doctorsList.addAll((Collection<? extends DNAData>) results.values);
            notifyDataSetChanged();
        }
    };

    class Holder extends RecyclerView.ViewHolder{

        ItemDoctorCardBinding binding;
        public Holder(@NonNull ItemDoctorCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
