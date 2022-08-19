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

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.NurseReplyAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.NurseReply;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentNurseCaseMMeasurementBinding;

import java.util.ArrayList;

public class NurseCaseMMeasurementFragment extends Fragment {
    FragmentNurseCaseMMeasurementBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    ArrayList<NurseReply> replies;
    NurseReplyAdaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse_case_m_measurement, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);
        replies = new ArrayList<>();

        caseDetailsViewModel.getCaseMLiveData().observe(getViewLifecycleOwner(), new Observer<CaseData>() {
            @Override
            public void onChanged(CaseData caseData) {
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

        binding.btnNurseCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nurseCaseMMeasurementFragment_to_nurseCaseDFragment);
            }
        });

        binding.ibNCDAddMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nurseCaseMMeasurementFragment_to_nurseAddMesFragment);
            }
        });
    }
}