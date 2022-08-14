package com.example.hospitalsystem_abdelrahmantarek.Nurse;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.MenusViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentNurseMenuBinding;


public class NurseMenuFragment extends Fragment {

    FragmentNurseMenuBinding binding;
    NavController navController;
    MenusViewModel menusViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        menusViewModel = new MenusViewModel(binding.getRoot().getContext());

        binding.tvNMEmpName.setText(menusViewModel.getEmployeeModel().getFullName());

        binding.ibContainerGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nurseMenuFragment_to_tasksListFragment);
            }
        });

        binding.ibContainerPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nurseMenuFragment_to_reportsListsFragment);
            }
        });

        binding.ibNMLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menusViewModel.resetPreference();
                navController.navigate(R.id.action_nurseMenuFragment_to_loginFragment);
            }
        });
    }
}