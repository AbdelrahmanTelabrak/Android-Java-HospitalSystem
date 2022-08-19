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

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.AnlReplyAdapter;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.AnalysisReply;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.EndCaseViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocCRecordBinding;

import java.util.ArrayList;

public class DocCRecordFragment extends Fragment {
    FragmentDocCRecordBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    EndCaseViewModel endCaseViewModel;
    AnalysisReply reply;
    AnlReplyAdapter adapter;
    int caseId;
    private static final String TAG = "DocCRecordFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_c_record, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);
        caseDetailsObserver();

        endCaseViewModel = new ViewModelProvider(this).get(EndCaseViewModel.class);
        endCaseObserver();

        binding.btnDCRCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_docCRecordFragment_to_docCaseDFragment);
            }
        });

        binding.btnDCRMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_docCRecordFragment_to_docCaseMeasurementFragment);
            }
        });

        binding.btnDCREndCase.setOnClickListener(new View.OnClickListener() {
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
                if(!caseData.getAnalysisId().equals("")){
                    if(!caseData.getImage().equals("http://api.instant-ss.com/public")){
                        Log.i(TAG, "onChanged: success");
                        reply = new AnalysisReply(caseData.getAnalysisId(), caseData.getImage());
                        Log.i(TAG, "onChanged: emp name = "+ reply.getEmpName());
                        Log.i(TAG, "onChanged: image = "+ reply.getImageUrl());
                        ArrayList<AnalysisReply> list = new ArrayList<>();
                        list.add(reply);
                        Log.i(TAG, "onChanged: list size = "+ list.size());
                        adapter = new AnlReplyAdapter(list, requireContext());
                        binding.rvDCRReply.setAdapter(adapter);
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

    public void endCaseObserver(){
        endCaseViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                navController.navigate(R.id.action_docCRecordFragment_to_docCasesListFragment);
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