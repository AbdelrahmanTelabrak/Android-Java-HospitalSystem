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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.DocCasesListAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Manager.CasesListViewModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseCardData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CasesResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocCallsListBinding;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocCasesListBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DocCasesListFragment extends Fragment {

    FragmentDocCasesListBinding binding;
    NavController navController;
    CasesListViewModel casesListViewModel;
    DocCasesListAdaptor casesListAdaptor;
    CaseDetailsViewModel caseDetailsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_cases_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);

        casesListViewModel = new ViewModelProvider(this).get(CasesListViewModel.class);
        casesListViewModel.getCases(binding.getRoot().getContext());
        casesListObserver();

        binding.btnDclBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_docCasesListFragment_to_doctorMenuFragment);
            }
        });
    }

    public void casesListObserver(){
        casesListViewModel.getCasesListLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<CaseCardData>>() {
            @Override
            public void onChanged(ArrayList<CaseCardData> caseCardData) {
                casesListAdaptor = new DocCasesListAdaptor(caseCardData, binding.getRoot().getContext(), caseDetailsViewModel);
                binding.rvDclCases.setAdapter(casesListAdaptor);
            }
        });

        casesListViewModel.getFailedResponseLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(binding.getRoot().getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}