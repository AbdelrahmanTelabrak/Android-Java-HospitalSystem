package com.example.hospitalsystem_abdelrahmantarek.Nurse;

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
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentNurseCaseDBinding;


public class NurseCaseDFragment extends Fragment {
    FragmentNurseCaseDBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    String mNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse_case_d, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        navController.popBackStack(R.id.nurseCaseMMeasurementFragment, true);
        navController.popBackStack(R.id.nurseAddMesFragment, true);

        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);
        caseDetailsObserver();

        binding.btnNurseMMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nurseCaseDFragment_to_nurseCaseMMeasurementFragment);
            }
        });

        binding.ibNCDAddMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nurseCaseDFragment_to_nurseAddMesFragment);
            }
        });
    }

    public void caseDetailsObserver(){
        caseDetailsViewModel.getCaseMLiveData().observe(getViewLifecycleOwner(), new Observer<CaseData>() {
            @Override
            public void onChanged(CaseData caseData) {
                binding.nurseCDTvName.setText(caseData.getPatientName());
                binding.nurseCDTvAge.setText(caseData.getAge());
                binding.nurseCDTvPhone.setText(caseData.getPhone());
                binding.nurseCDTvDate.setText(caseData.getCreatedAt());
                binding.nurseCDTvStatus.setText(caseData.getCaseStatus());
                binding.nurseCDTvCaseDescription.setText(caseData.getDescription());
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