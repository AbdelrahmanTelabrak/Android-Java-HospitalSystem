package com.example.hospitalsystem_abdelrahmantarek.AnalysisEmp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.DocCasesListAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CasesListViewModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseCardData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentAnlCasesListBinding;

import java.util.ArrayList;

public class AnlCasesListFragment extends Fragment {
    FragmentAnlCasesListBinding binding;
    NavController navController;
    CasesListViewModel casesListViewModel;
    CaseDetailsViewModel caseDetailsViewModel;
    DocCasesListAdaptor casesListAdaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_anl_cases_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);
        casesListViewModel = new ViewModelProvider(this).get(CasesListViewModel.class);
        casesListViewModel.getCases(requireContext());
        casesListObserver();
    }

    public void casesListObserver(){
        casesListViewModel.getCasesListLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<CaseCardData>>() {
            @Override
            public void onChanged(ArrayList<CaseCardData> caseCardData) {
                casesListAdaptor = new DocCasesListAdaptor(caseCardData, binding.getRoot().getContext(), caseDetailsViewModel);
                binding.rvACLCases.setAdapter(casesListAdaptor);
            }
        });

        casesListViewModel.getFailedResponseLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(binding.getRoot().getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}