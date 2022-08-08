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
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentReceptionistMenuBinding;


public class ReceptionistMenuFragment extends Fragment {

    FragmentReceptionistMenuBinding binding;

    NavController navController;

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
        SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
        navController = Navigation.findNavController(view);

        binding.ibContainerDarkBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_receptionistMenuFragment_to_callsFragment);
            }
        });

        binding.ibRMLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putString("employee", "null").apply();
                navController.navigate(R.id.action_receptionistMenuFragment_to_loginFragment);
            }
        });
    }
}