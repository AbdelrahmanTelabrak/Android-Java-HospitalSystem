package com.example.hospitalsystem_abdelrahmantarek.Doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.EndCaseViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocCaseDBinding;


public class DocCaseDFragment extends Fragment {

    FragmentDocCaseDBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    EndCaseViewModel endCaseViewModel;
    int caseId;
    private static final String TAG = "DocCaseDFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_case_d, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Log.i(TAG, "onViewCreated: ");
        navController.popBackStack(R.id.docCaseMeasurementFragment, true);
        navController.popBackStack(R.id.docCRecordFragment, true);
        navController.popBackStack(R.id.docSelectNurseFragment, true);

        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);
        endCaseViewModel = new ViewModelProvider(this).get(EndCaseViewModel.class);
        endCaseObserver();

        caseDetailsObserver();
        binding.btnDCDMMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_docCaseDFragment_to_docCaseMeasurementFragment);
            }
        });

        binding.btnDCDRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_docCaseDFragment_to_docCRecordFragment);
            }
        });

        binding.docCDBtnAddNurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocCaseDFragmentDirections.ActionDocCaseDFragmentToDocSelectNurseFragment action =
                        DocCaseDFragmentDirections.actionDocCaseDFragmentToDocSelectNurseFragment(caseId);
                navController.navigate(action);
            }
        });

        binding.docCDBtnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_docCaseDFragment_to_requestBottomSheetFragment);
            }
        });

        binding.docCDBtnEndCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endCaseViewModel.endCase(requireContext(), caseId);
            }
        });
    }

    public void caseDetailsObserver(){
        caseDetailsViewModel.getCaseMLiveData().observe(getViewLifecycleOwner(), new Observer<CaseData>() {
            @Override
            public void onChanged(CaseData caseData) {
                caseId = caseData.getId();
                binding.docCDTvName.setText(caseData.getPatientName());
                binding.docCDTvAge.setText(caseData.getAge());
                binding.docCDTvPhone.setText(caseData.getPhone());
                binding.docCDTvDate.setText(caseData.getCreatedAt());
                binding.docCDTvStatus.setText(caseData.getCaseStatus());
                binding.docCDTvCaseDescription.setText(caseData.getDescription());
            }
        });

        caseDetailsViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void endCaseObserver(){
        endCaseViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                navController.navigate(R.id.action_docCaseDFragment_to_docCasesListFragment);
            }
        });

        endCaseViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}