package com.example.hospitalsystem_abdelrahmantarek;

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
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentPrototypeMapBinding;

public class PrototypeMapFragment extends Fragment {

    FragmentPrototypeMapBinding binding;

    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prototype_map, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
/*
        binding.btnDoctorPrototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_prototypeMapFragment_to_doctorMenuFragment);
            }
        });

        binding.btnReceptionistPrototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_prototypeMapFragment_to_receptionistMenuFragment);
            }
        });

        binding.btnNursePrototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_prototypeMapFragment_to_nurseMenuFragment);
            }
        });

        binding.btnAnalysisPrototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_prototypeMapFragment_to_analysisEmpMenuFragment);
            }
        });

        binding.btnManagerPrototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_prototypeMapFragment_to_managerMenuFragment);
            }
        });

        binding.btnHrPrototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate((R.id.action_prototypeMapFragment_to_mainMenuHrFragment));
            }
        });*/

    }
}