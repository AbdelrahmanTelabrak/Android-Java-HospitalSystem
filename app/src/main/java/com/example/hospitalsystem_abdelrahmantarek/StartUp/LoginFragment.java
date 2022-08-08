package com.example.hospitalsystem_abdelrahmantarek.StartUp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentLoginBinding;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    FragmentLoginBinding binding;
    NavController navController;

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



        binding.btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etlLoginEmail.setErrorEnabled(false);
                binding.etlLoginPassword.setErrorEnabled(false);

                LoginRequest loginRequest = new LoginRequest(binding.etiLoginEmail.getText().toString(),
                        binding.etiLoginPassword.getText().toString(), "sdfsfsdfsdfsdfsfs");

                Log.i(TAG, "onClick: "+ binding.etiLoginEmail.getText().toString());
                Log.i(TAG, "onClick: "+ binding.etiLoginPassword.getText().toString());

                RetrofitClient.getClient().login(loginRequest).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful()){
                            Log.i(TAG, "onResponse: is Successful");
                            if(binding.etiLoginEmail.getText().toString().isEmpty()) {
                                binding.etlLoginEmail.setError("Email is required");
                            }if (binding.etiLoginPassword.getText().toString().isEmpty())
                            {
                                binding.etlLoginPassword.setError("Password is required");
                            }
                            if(!binding.etiLoginEmail.getText().toString().isEmpty() && !binding.etiLoginPassword.getText().toString().isEmpty())
                                handleSuccessResponse(response);
                        }
                        else {
                            Log.i(TAG, "onResponse: is failed");
                            handleFailedResponse(response);
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(v.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void handleFailedResponse(Response<LoginResponse> response) {
        try {
            Log.i(TAG, "handleFailedResponse: 1");
            String errorResponse = response.errorBody().string();

            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSuccessResponse(Response<LoginResponse> response) {
        //save login data in shared preferences
        if(response.body().isSuccess()){
            SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
            EmployeeModel employeeModel = new EmployeeModel(response.body().getData().getId(), response.body().getData().getFirstName(),
                    response.body().getData().getLastName(), response.body().getData().getMobile(), response.body().getData().getEmail(),
                    response.body().getData().getGender(), response.body().getData().getStatus(), response.body().getData().getType(),
                    response.body().getData().getBirthday(), response.body().getData().getAddress(), response.body().getData().getCreatedAt(),
                    response.body().getData().getVerified(), response.body().getData().getTokenType(), response.body().getData().getAccessToken());
            String data = new Gson().toJson(employeeModel);
            preferences.edit().putString("employee", data).apply();
            handleEmployeeType(response);
        }
        else {
            String errorMessage = response.body().getMessage();
            Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleEmployeeType(Response<LoginResponse> response){
        String empType = response.body().getData().getType();
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