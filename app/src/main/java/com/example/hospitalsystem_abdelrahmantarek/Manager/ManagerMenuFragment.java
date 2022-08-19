package com.example.hospitalsystem_abdelrahmantarek.Manager;

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
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentManagerMenuBinding;

public class ManagerMenuFragment extends Fragment {

    FragmentManagerMenuBinding binding;
    NavController navController;
    MenusViewModel menusViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manager_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        menusViewModel = new MenusViewModel(binding.getRoot().getContext());

        binding.tvMMFullName.setText(menusViewModel.getEmployeeModel().getFullName());

        binding.ibRectangleOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_managerMenuFragment_to_employeeListFragment);
            }
        });

        binding.ibContainerGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_managerMenuFragment_to_managerTasksFragment);
            }
        });

        binding.ibContainerDarkBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_managerMenuFragment_to_managerCasesFragment);
            }
        });

        binding.ibContainerPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_managerMenuFragment_to_reportsListsFragment);
            }
        });

        binding.ibContainerBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_managerMenuFragment_to_attendanceMenuFragment);
            }
        });

        binding.ivPpManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_managerMenuFragment_to_profileFragment);
            }
        });

        binding.ibMMLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menusViewModel.resetPreference();
                navController.navigate(R.id.action_managerMenuFragment_to_loginFragment);
            }
        });
    }
}