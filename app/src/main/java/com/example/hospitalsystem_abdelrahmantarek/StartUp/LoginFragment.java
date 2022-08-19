package com.example.hospitalsystem_abdelrahmantarek.StartUp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginRequest;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Authentication.LoginViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    FragmentLoginBinding binding;
    NavController navController;
    LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.etiLoginEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence!=null)
                    binding.etlLoginEmail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etiLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence!=null)
                    binding.etlLoginPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etlLoginEmail.setErrorEnabled(false);
                binding.etlLoginPassword.setErrorEnabled(false);
                int verify=0;
                if(binding.etiLoginEmail.getText().toString().isEmpty()) {
                    binding.etlLoginEmail.setError("Email is required");
                    verify+=1;
                }
                if (binding.etiLoginPassword.getText().toString().isEmpty()) {
                    binding.etlLoginPassword.setError("Password is required");
                    verify+=1;
                }
                if(verify==0) {
                    LoginRequest loginRequest = new LoginRequest(binding.etiLoginEmail.getText().toString(),
                            binding.etiLoginPassword.getText().toString(), "sdfsfsdfsdfsdfsfs");
                    loginViewModel.login(loginRequest);
                }
                else
                    return;
                Log.i(TAG, "onClick: "+ binding.etiLoginEmail.getText().toString());
                Log.i(TAG, "onClick: "+ binding.etiLoginPassword.getText().toString());
            }
        });
        loginObserver();
    }

    private void loginObserver(){
        loginViewModel.getLoginSuccess().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
                preferences.edit().putString("employee", s).apply();
            }
        });
        loginViewModel.getLoginFailed().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(binding.getRoot().getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
        loginViewModel.getEmpType().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                handleEmployeeType(s);
            }
        });
    }

    private void handleEmployeeType(String empType){
        if(empType.equals("doctor")){
            navController.navigate(R.id.action_loginFragment_to_doctorMenuFragment);
        }else if(empType.equals("nurse")){
            navController.navigate(R.id.action_loginFragment_to_nurseMenuFragment);
        }else if(empType.equals("receptionist")){
            navController.navigate(R.id.action_loginFragment_to_receptionistMenuFragment);
        }else if(empType.equals("hr")){
            navController.navigate(R.id.action_loginFragment_to_mainMenuHrFragment);
        }else if(empType.equals("analysis")){
            navController.navigate(R.id.action_loginFragment_to_analysisEmpMenuFragment);
        }else if(empType.trim().toLowerCase().equals("manager")||empType.equals("manger")){
            navController.navigate(R.id.action_loginFragment_to_managerMenuFragment);
        }
    }
}