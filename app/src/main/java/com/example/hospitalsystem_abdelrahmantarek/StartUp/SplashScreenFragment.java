package com.example.hospitalsystem_abdelrahmantarek.StartUp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentSplashScreenBinding;
import com.google.gson.Gson;


public class SplashScreenFragment extends Fragment {

    FragmentSplashScreenBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
        String empJson = preferences.getString("employee", "null");

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (empJson.equals("null")){
                    navController.navigate(R.id.action_splashScreenFragment_to_loginFragment);
                }
                else{
                    EmployeeModel employeeModel = new Gson().fromJson(empJson, EmployeeModel.class);
                    handleEmployeeType(employeeModel.getType());
                }
            }
        },2000);
    }


    private void handleEmployeeType(String empType){
        if(empType.equals("doctor")){
            navController.navigate(R.id.action_splashScreenFragment_to_doctorMenuFragment);
        }else if(empType.equals("nurse")){
            navController.navigate(R.id.action_splashScreenFragment_to_nurseMenuFragment);
        }else if(empType.equals("receptionist")){
            navController.navigate(R.id.action_splashScreenFragment_to_receptionistMenuFragment);
        }else if(empType.equals("hr")){
            navController.navigate(R.id.action_splashScreenFragment_to_mainMenuHrFragment);
        }else if(empType.equals("analysis")){
            navController.navigate(R.id.action_splashScreenFragment_to_analysisEmpMenuFragment);
        }else if(empType.equals("manger")){
            navController.navigate(R.id.action_splashScreenFragment_to_managerMenuFragment);
        }
    }
}