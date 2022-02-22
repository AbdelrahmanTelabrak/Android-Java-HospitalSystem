package com.example.hospitalsystem_abdelrahmantarek.Manager;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentMainMenuHrBinding;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentManagerMenuBinding;

public class ManagerMenuFragment extends Fragment {

    FragmentManagerMenuBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manager_menu, container, false);
        return binding.getRoot();
    }
}