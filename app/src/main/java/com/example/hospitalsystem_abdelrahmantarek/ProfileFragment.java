package com.example.hospitalsystem_abdelrahmantarek;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospitalsystem_abdelrahmantarek.ViewModels.MenusViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    MenusViewModel menusViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menusViewModel = new MenusViewModel(requireContext());

        handlePp(menusViewModel.getEmployeeModel().getType());
        binding.tvProfileName.setText(menusViewModel.getEmployeeModel().getFullName());
        binding.tvProfileSpecialist.setText(menusViewModel.getEmployeeModel().getType());
        binding.tvProfileGender.setText(menusViewModel.getEmployeeModel().getGender());
        binding.tvProfileBirthdate.setText(menusViewModel.getEmployeeModel().getBirthday());
        binding.tvProfileAddress.setText(menusViewModel.getEmployeeModel().getAddress());
        binding.tvProfileStatus.setText(menusViewModel.getEmployeeModel().getStatus());
        binding.tvProfileEmail.setText(menusViewModel.getEmployeeModel().getEmail());
        binding.tvProfilePhone.setText(menusViewModel.getEmployeeModel().getMobile());
    }

    private void handlePp(String empType){
        if(empType.toLowerCase().equals("doctor"))
            binding.ivProfilePicture.setImageResource(R.drawable.iv_dpp_doctor);
        else if(empType.toLowerCase().equals("nurse"))
            binding.ivProfilePicture.setImageResource(R.drawable.iv_dpp_nurse);
        else if(empType.toLowerCase().equals("receptionist"))
            binding.ivProfilePicture.setImageResource(R.drawable.iv_dpp_receptionist);
        else if(empType.toLowerCase().equals("analysis"))
            binding.ivProfilePicture.setImageResource(R.drawable.iv_dpp_analysis);
        else if(empType.toLowerCase().equals("manger"))
            binding.ivProfilePicture.setImageResource(R.drawable.iv_dpp_manager);
        else if(empType.toLowerCase().equals("hr"))
            binding.ivProfilePicture.setImageResource(R.drawable.iv_dpp_hr);
    }
}