package com.example.hospitalsystem_abdelrahmantarek.Manager;

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
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentManagerCMesBinding;

import java.util.ArrayList;

public class ManagerCMesFragment extends Fragment {
    FragmentManagerCMesBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    NurseReplyAdaptor adaptor;
    ArrayList<NurseReply> replies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manager_c_mes, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);
        caseDetailsObserver();

        replies = new ArrayList<>();

        binding.btnMCMCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_managerCMesFragment_to_managerCDFragment);
            }
        });

        binding.btnMCMRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_managerCMesFragment_to_managerCRecordFragment);
            }
        });
    }

    public void caseDetailsObserver(){
        caseDetailsViewModel.getCaseMLiveData().observe(getViewLifecycleOwner(), new Observer<CaseData>() {
            @Override
            public void onChanged(CaseData caseData) {
                if(!caseData.getNurseId().equals("")){
                    NurseReply reply = new NurseReply(caseData.getNurseId(), caseData.getMeasurementNote(), caseData.getBloodPressure(),
                            caseData.getSugarAnalysis(), caseData.getTempreture(), caseData.getFluidBalance(), caseData.getRespiratoryRate(),
                            caseData.getHeartRate());
                    replies.add(reply);
                    adaptor = new NurseReplyAdaptor(replies);
                    binding.rvMCMNurseReply.setAdapter(adaptor);
                }
            }
        });
    }
}