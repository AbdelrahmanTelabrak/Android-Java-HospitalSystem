package com.example.hospitalsystem_abdelrahmantarek.StartUp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentSplashScreenBinding;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreenFragment extends Fragment {

    FragmentSplashScreenBinding binding;
    NavController navController;
    private String empJson;
    private Timer timer;
    private TimerTask timerTask;
    final Handler handler = new Handler();
    int i=0;

    private static final String TAG = "SplashScreenFragment";

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
        empJson = preferences.getString("employee", "null");
        startTimer();

        //setProgressValue(i);
//        setProgressValue2(i);


//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (empJson.equals("null")){
//                    navController.navigate(R.id.action_splashScreenFragment_to_loginFragment);
//                }
//                else{
//                    EmployeeModel employeeModel = new Gson().fromJson(empJson, EmployeeModel.class);
//                    handleEmployeeType(employeeModel.getType());
//                }
//            }
//        },3000);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                while(i<=100) {
//                    Log.i(TAG, "startTimer: i= " + i);
//                    if (i == 100) {
//                        binding.linearProgressIndicator.setProgress(i);
//
//                        if (empJson.equals("null")) {
//                            navController.navigate(R.id.action_splashScreenFragment_to_loginFragment);
//                        } else {
//                            EmployeeModel employeeModel = new Gson().fromJson(empJson, EmployeeModel.class);
//                            handleEmployeeType(employeeModel.getType());
//                        }
//                    }
//                    else {
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                binding.linearProgressIndicator.setProgress(i);
//                                i += 20;
//                            }
//                        }, 1000);
//                    }
//                }
//            }
//        });
//        thread.start();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 500, 500);
    }

    private void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(i>100){
                            timer.cancel();
                            if (empJson.equals("null")){
                                navController.navigate(R.id.action_splashScreenFragment_to_loginFragment);

                            }
                            else{
                                EmployeeModel employeeModel = new Gson().fromJson(empJson, EmployeeModel.class);
                                handleEmployeeType(employeeModel.getType());
                            }
                        }
                        else{
                            binding.linearProgressIndicator.setProgress(i);
                            i+=20;
                        }
                    }
                });
            }
        };
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

    private void setProgressValue(final int progress) {

        // set the progress
        binding.linearProgressIndicator.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 20);
                if (empJson.equals("null")){
                    navController.navigate(R.id.action_splashScreenFragment_to_loginFragment);
                }
                else{
                    EmployeeModel employeeModel = new Gson().fromJson(empJson, EmployeeModel.class);
                    handleEmployeeType(employeeModel.getType());
                }
            }
        });
        thread.start();
    }

    private void setProgressValue2(final int progress){
        binding.linearProgressIndicator.setProgress(progress);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setProgressValue2(progress+20);
            }
        },500);
        if (empJson.equals("null")){
            navController.navigate(R.id.action_splashScreenFragment_to_loginFragment);
        }
        else{
            EmployeeModel employeeModel = new Gson().fromJson(empJson, EmployeeModel.class);
            handleEmployeeType(employeeModel.getType());
        }
    }
}