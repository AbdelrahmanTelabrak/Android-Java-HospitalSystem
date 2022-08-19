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

import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.AddMeasurementRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.AddMeasureViewModel;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentNurseAddMesBinding;


public class NurseAddMesFragment extends Fragment {
    FragmentNurseAddMesBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    AddMeasureViewModel addMeasureViewModel;
    int caseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse_add_mes, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);
        caseDetailsObserver();
        addMeasureViewModel = new ViewModelProvider(this).get(AddMeasureViewModel.class);
        addMObserver();

        binding.btnNASAddM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etlNASBloodP.setErrorEnabled(false);
                binding.etlNASSugarA.setErrorEnabled(false);
                binding.etlNASMNote.setErrorEnabled(false);
                int verify = 0;
                if(binding.etiNASBloodP.getText().toString().isEmpty()){
                    binding.etlNASBloodP.setError("Blood pressure is required");
                    verify += 1;
                }
                if(binding.etiNASSugarA.getText().toString().isEmpty()){
                    binding.etlNASSugarA.setError("Sugar analysis is required");
                    verify+=1;
                }
                if(binding.etiNASMNote.getText().toString().isEmpty()){
                    binding.etlNASMNote.setError("Note is required");
                }

                if(verify==0){
                    AddMeasurementRequest request = new AddMeasurementRequest(caseId, binding.etiNASBloodP.getText().toString(),
                            binding.etiNASSugarA.getText().toString(), binding.etiNASMNote.getText().toString());
                    addMeasureViewModel.addMeasurement(requireContext(), request);
                }
            }
        });
    }

    public void addMObserver(){
        addMeasureViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                navController.navigate(R.id.action_nurseAddMesFragment_to_nurseCaseDFragment);
            }
        });

        addMeasureViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void caseDetailsObserver(){
        caseDetailsViewModel.getCaseMLiveData().observe(getViewLifecycleOwner(), new Observer<CaseData>() {
            @Override
            public void onChanged(CaseData caseData) {
                binding.tvNASDocName.setText(caseData.getDoctorId());
                binding.tvNASDescription.setText(caseData.getDescription());
                caseId = caseData.getId();
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