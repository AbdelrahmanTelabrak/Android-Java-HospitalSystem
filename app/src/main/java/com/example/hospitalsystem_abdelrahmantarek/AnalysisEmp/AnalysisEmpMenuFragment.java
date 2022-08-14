package com.example.hospitalsystem_abdelrahmantarek.AnalysisEmp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.MenusViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentAnalysisEmpMenuBinding;

public class AnalysisEmpMenuFragment extends Fragment {

    FragmentAnalysisEmpMenuBinding binding;
    NavController navController;
    MenusViewModel menusViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_analysis_emp_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        menusViewModel = new MenusViewModel(binding.getRoot().getContext());
        binding.tvAMEmpName.setText(menusViewModel.getEmployeeModel().getFullName());

        binding.ibContainerGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_analysisEmpMenuFragment_to_tasksListFragment);
            }
        });

        binding.ibContainerPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_analysisEmpMenuFragment_to_reportsListsFragment);
            }
        });

        binding.ivAMLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menusViewModel.resetPreference();
                navController.navigate(R.id.action_analysisEmpMenuFragment_to_loginFragment);
            }
        });
    }
}