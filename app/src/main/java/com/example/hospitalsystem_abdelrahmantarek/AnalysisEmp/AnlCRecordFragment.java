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

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.AnlReplyAdapter;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.AnalysisReply;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentAnlCRecordBinding;

import java.util.ArrayList;

public class AnlCRecordFragment extends Fragment {
    FragmentAnlCRecordBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    AnlReplyAdapter adapter;
    AnalysisReply reply;
    int caseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_anl_c_record, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);
        caseDetailsObserver();

        binding.ibACRAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnlCRecordFragmentDirections.ActionAnlCRecordFragmentToAnalysisAddMedRFragment action =
                        AnlCRecordFragmentDirections.actionAnlCRecordFragmentToAnalysisAddMedRFragment(caseId);
                navController.navigate(action);
            }
        });

        binding.btnACRCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_anlCRecordFragment_to_analysisCDFragment);
            }
        });
    }

    public void caseDetailsObserver(){
        caseDetailsViewModel.getCaseMLiveData().observe(getViewLifecycleOwner(), new Observer<CaseData>() {
            @Override
            public void onChanged(CaseData caseData) {
                caseId = caseData.getId();
                if(!caseData.getAnalysisId().equals("")){
                    if(!caseData.getImage().equals("http://api.instant-ss.com/public")){
                        reply = new AnalysisReply(caseData.getAnalysisId(), caseData.getImage());
                        ArrayList<AnalysisReply> list = new ArrayList<>();
                        list.add(reply);
                        adapter = new AnlReplyAdapter(list, requireContext());
                        binding.rvACRReply.setAdapter(adapter);
                    }
                }
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