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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.NurseReplyAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.NurseReply;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.EndCaseViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocCaseMeasurementBinding;

import java.util.ArrayList;

public class DocCaseMeasurementFragment extends Fragment {
    FragmentDocCaseMeasurementBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    EndCaseViewModel endCaseViewModel;
    NurseReplyAdaptor adaptor;
    ArrayList<NurseReply> replies;
    int caseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_case_measurement, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);

        endCaseViewModel = new ViewModelProvider(this).get(EndCaseViewModel.class);
        endCaseObserver();

        replies = new ArrayList<>();

        caseDetailsViewModel.getCaseMLiveData().observe(getViewLifecycleOwner(), new Observer<CaseData>() {
            @Override
            public void onChanged(CaseData caseData) {
                caseId = caseData.getId();
                if(!caseData.getNurseId().equals("")){
                    NurseReply reply = new NurseReply(caseData.getNurseId(), caseData.getMeasurementNote(), caseData.getBloodPressure(),
                            caseData.getSugarAnalysis(), caseData.getTempreture(), caseData.getFluidBalance(), caseData.getRespiratoryRate(),
                            caseData.getHeartRate());
                    replies.add(reply);
                    adaptor = new NurseReplyAdaptor(replies);
                    binding.rvDCMMNurseReply.setAdapter(adaptor);
                }
            }
        });

        binding.btnDCDCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_docCaseMeasurementFragment_to_docCaseDFragment);
            }
        });

        binding.btnDCMRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_docCaseMeasurementFragment_to_docCRecordFragment);
            }
        });

        binding.btnDCMEndCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endCaseViewModel.endCase(requireContext(), caseId);
            }
        });
    }

    public void endCaseObserver(){
        endCaseViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                navController.navigate(R.id.action_docCaseMeasurementFragment_to_docCasesListFragment);
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