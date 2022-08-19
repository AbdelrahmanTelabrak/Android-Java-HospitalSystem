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

import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentAnalysisCDBinding;


public class AnalysisCDFragment extends Fragment {
    FragmentAnalysisCDBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    int caseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_analysis_c_d, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);
        caseDetailsObserver();

        binding.ibACDAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnalysisCDFragmentDirections.ActionAnalysisCDFragmentToAnalysisAddMedRFragment action =
                        AnalysisCDFragmentDirections.actionAnalysisCDFragmentToAnalysisAddMedRFragment(caseId);
                navController.navigate(action);
            }
        });

        binding.btnACDRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_analysisCDFragment_to_anlCRecordFragment);
            }
        });
    }

    public void caseDetailsObserver(){
        caseDetailsViewModel.getCaseMLiveData().observe(getViewLifecycleOwner(), new Observer<CaseData>() {
            @Override
            public void onChanged(CaseData caseData) {
                caseId = caseData.getId();
                binding.analysisCDTvName.setText(caseData.getPatientName());
                binding.analysisCDTvAge.setText(caseData.getAge());
                binding.analysisCDTvPhone.setText(caseData.getPhone());
                binding.analysisCDTvDate.setText(caseData.getCreatedAt());
                binding.analysisCDTvStatus.setText(caseData.getCaseStatus());
                binding.analysisCDTvCaseDescription.setText(caseData.getDescription());
            }
        });

        caseDetailsViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}