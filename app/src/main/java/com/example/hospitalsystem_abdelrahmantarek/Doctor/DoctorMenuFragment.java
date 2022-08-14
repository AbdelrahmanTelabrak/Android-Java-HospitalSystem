package com.example.hospitalsystem_abdelrahmantarek.Doctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.MenusViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDoctorMenuBinding;
import com.google.gson.Gson;


public class DoctorMenuFragment extends Fragment {

    FragmentDoctorMenuBinding binding;
    NavController navController;
    MenusViewModel menusViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doctor_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        menusViewModel = new MenusViewModel(binding.getRoot().getContext());

        binding.tvDMDoctorName.setText(menusViewModel.getEmployeeModel().getFullName());

        binding.ibContainerDarkBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_doctorMenuFragment_to_docCallsListFragment);
            }
        });

        binding.ibRectangleOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_doctorMenuFragment_to_docCasesListFragment);
            }
        });
        binding.ibContainerGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_doctorMenuFragment_to_tasksListFragment);
            }
        });

        binding.ibContainerPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_doctorMenuFragment_to_reportsListsFragment);
            }
        });

        binding.ivDMLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menusViewModel.resetPreference();
                navController.navigate(R.id.action_doctorMenuFragment_to_loginFragment);
            }
        });

    }
}