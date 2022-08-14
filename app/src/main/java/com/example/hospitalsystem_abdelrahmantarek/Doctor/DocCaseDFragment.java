package com.example.hospitalsystem_abdelrahmantarek.Doctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CreateCallResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.ShowCaseResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocCaseDBinding;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DocCaseDFragment extends Fragment {

    FragmentDocCaseDBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    int caseId;

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
        navController.popBackStack(R.id.docCaseMeasurementFragment, true);
        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);

        caseId= DocCaseDFragmentArgs.fromBundle(getArguments()).getCaseId();

        caseDetailsObserver();
        binding.btnDCDMMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_docCaseDFragment_to_docCaseMeasurementFragment);
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

}