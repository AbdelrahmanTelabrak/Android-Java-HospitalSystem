package com.example.hospitalsystem_abdelrahmantarek.HrEmployee;

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

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.MenusViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentMainMenuHrBinding;


public class MainMenuHrFragment extends Fragment {

    FragmentMainMenuHrBinding binding;
    NavController navController;
    MenusViewModel menusViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu_hr, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        navController.popBackStack(R.id.splashScreenFragment, true);
        menusViewModel = new MenusViewModel(binding.getRoot().getContext());

        binding.tvHrMEmpName.setText(menusViewModel.getEmployeeModel().getFullName());

        binding.ibContainerOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_mainMenuHrFragment_to_employeeListFragment);
            }
        });

        binding.ibContainerGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_mainMenuHrFragment_to_tasksListFragment);
            }
        });

        binding.ibContainerPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_mainMenuHrFragment_to_reportsListsFragment);
            }
        });

        binding.ibContainerBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_mainMenuHrFragment_to_attendanceMenuFragment);
            }
        });

        binding.ivProfilePhotoHr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_mainMenuHrFragment_to_profileFragment);
            }
        });

        binding.ibHMLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menusViewModel.resetPreference();
                navController.navigate(R.id.action_mainMenuHrFragment_to_loginFragment);
            }
        });
    }
}