package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Doctor.DocCasesListFragmentDirections;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseCardData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemCaseCardBinding;

import java.util.ArrayList;

public class DocCasesListAdaptor extends RecyclerView.Adapter<DocCasesListAdaptor.Holder> {
    ArrayList<CaseCardData> casesList;

    public DocCasesListAdaptor(ArrayList<CaseCardData> casesList) {
        this.casesList = casesList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCaseCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_case_card, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CaseCardData cardData = casesList.get(position);
        holder.binding.itemDocCaseTvName.setText(cardData.getPatientName());
        holder.binding.itemDocCaseTvBirthdate.setText(cardData.getCreatedAt());
        holder.binding.btnItemCCShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                DocCasesListFragmentDirections.ActionDocCasesListFragmentToDocCaseDFragment action =
                        DocCasesListFragmentDirections.actionDocCasesListFragmentToDocCaseDFragment(cardData.getId().toString());
                navController.navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return casesList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemCaseCardBinding binding;
        public Holder(@NonNull ItemCaseCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
