package com.example.hospitalsystem_abdelrahmantarek.Receptionist;

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
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentReceptionistMenuBinding;


public class ReceptionistMenuFragment extends Fragment {

    FragmentReceptionistMenuBinding binding;
    NavController navController;
    MenusViewModel menusViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receptionist_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        menusViewModel = new MenusViewModel(binding.getRoot().getContext());

        binding.tvRMEmpName.setText(menusViewModel.getEmployeeModel().getFullName());

        binding.ibContainerGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_receptionistMenuFragment_to_tasksListFragment);
            }
        });

        binding.ibContainerDarkBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_receptionistMenuFragment_to_callsFragment);
            }
        });

        binding.ibContainerPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_receptionistMenuFragment_to_reportsListsFragment);
            }
        });

        binding.ibContainerBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_receptionistMenuFragment_to_attendanceMenuFragment);
            }
        });

        binding.ivPpReceptionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_receptionistMenuFragment_to_profileFragment2);
            }
        });

        binding.ibRMLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menusViewModel.resetPreference();
                navController.navigate(R.id.action_receptionistMenuFragment_to_loginFragment);
            }
        });
    }
}