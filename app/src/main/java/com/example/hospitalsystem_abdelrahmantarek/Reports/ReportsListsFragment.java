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

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.ReportsAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.ReportCardData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.MenusViewModel;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Reports.ReportsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentReportsListsBinding;

import java.util.ArrayList;


public class ReportsListsFragment extends Fragment {
    FragmentReportsListsBinding binding;
    NavController navController;
    ReportsViewModel reportsViewModel;
    MenusViewModel menusViewModel;
    ReportsAdaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reports_lists, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        navController.popBackStack(R.id.createReportFragment, true);
        navController.popBackStack(R.id.reportDetailsFragment, true);
        navController.popBackStack(R.id.reportDateBSFragment, true);
        String date = "";
        if(!ReportsListsFragmentArgs.fromBundle(getArguments()).getDate().equals("null")) {
            date = ReportsListsFragmentArgs.fromBundle(getArguments()).getDate();
            binding.etiRLDate.setText(date);
        }
        reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        menusViewModel = new MenusViewModel(requireContext());
        reportsViewModel.getReports(date, binding.getRoot().getContext());
        reportsObserver();

        binding.ibReportsChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_reportsListsFragment_to_reportDateBSFragment);
            }
        });

        binding.ibAddReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!menusViewModel.getEmployeeModel().getType().toLowerCase().equals("manger")){
                    navController.navigate(R.id.action_reportsListsFragment_to_createReportFragment);
                }
            }
        });
    }

    public void reportsObserver(){
        reportsViewModel.getReportsListMLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ReportCardData>>() {
            @Override
            public void onChanged(ArrayList<ReportCardData> reportCardData) {
                adaptor = new ReportsAdaptor(reportCardData);
                binding.rvRLReports.setAdapter(adaptor);
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