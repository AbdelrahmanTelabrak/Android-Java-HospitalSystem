package com.example.hospitalsystem_abdelrahmantarek.ViewModels.Authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.RegisterRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.RegisterResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private EmployeeModel employeeModel;
    private MutableLiveData<String> errorMLiveData = new MutableLiveData<>();
    private MutableLiveData<String> successMLiveData = new MutableLiveData<>();

    public void register(Context context, RegisterRequest registerRequest){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);

        RetrofitClient.getClient().register(registerRequest, employeeModel.getAccessToken()).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful())
                {
                    handleSuccessResponse(response);
                }
                else
                {
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                errorMLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public LiveData<String> getErrorMLiveData() {
        return errorMLiveData;
    }

    public LiveData<String> getSuccessMLiveData() {
        return successMLiveData;
    }

    private void handleSuccessResponse(Response<RegisterResponse> response) {
        //save login data in shared preferences

        if(response.body().isSuccess()){
            successMLiveData.postValue("Success");
        }
        else {
            String errorMessage = response.body().getMessage();
            errorMLiveData.postValue(errorMessage);
        }
    }

    private void handleFailedResponse(Response<RegisterResponse> response) {
        try {
            String errorResponse = response.errorBody().string();
            System.out.println("ERROR RESPONSE*************************"+errorResponse);

            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            errorMLiveData.postValue(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
