package com.example.hospitalsystem_abdelrahmantarek.Reports;

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

import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.CreateReportRequest;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Reports.ReportsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentCreateReportBinding;


public class CreateReportFragment extends Fragment {
    FragmentCreateReportBinding binding;
    NavController navController;
    ReportsViewModel reportsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_report, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        reportObserver();

        binding.btnCRCreateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etlCRReportName.setErrorEnabled(false);
                binding.etlCRReportName.setErrorEnabled(false);
                int verify=0;
                if(binding.etiCRReportName.getText().toString().isEmpty()) {
                    binding.etiCRReportName.setError("Email is required");
                    verify+=1;
                }
                if (binding.etiCRDescription.getText().toString().isEmpty()) {
                    binding.etiCRDescription.setError("Password is required");
                    verify+=1;
                }
                if(verify==0) {
                    CreateReportRequest request = new CreateReportRequest(binding.etiCRReportName.getText().toString(),
                            binding.etiCRDescription.getText().toString());
                    reportsViewModel.createReport(request);
                }
                else
                    return;
            }
        });
    }

    public void reportObserver(){
        reportsViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                navController.navigate(R.id.action_createReportFragment_to_reportsListsFragment);
            }
        });

        reportsViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}