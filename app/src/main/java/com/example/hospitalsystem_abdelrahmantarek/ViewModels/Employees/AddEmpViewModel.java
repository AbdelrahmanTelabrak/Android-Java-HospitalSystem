package com.example.hospitalsystem_abdelrahmantarek.ViewModels.Employees;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.AddNurseResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmpViewModel extends ViewModel {
    private EmployeeModel employeeModel;
    private MutableLiveData<String> successMLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMLiveData = new MutableLiveData<>();

    public void addEmp(int caseId, int empId, Context context){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);

        RetrofitClient.getClient().addNurse(caseId, empId, employeeModel.getAccessToken()).enqueue(new Callback<AddNurseResponse>() {
            @Override
            public void onResponse(Call<AddNurseResponse> call, Response<AddNurseResponse> response) {
                if(response.isSuccessful()){
                    if ((response.body().isSuccess())){
                        successMLiveData.postValue("Success");
                    }
                    else {
                        String errorMessage = response.body().getMessage();
                        errorMLiveData.postValue(errorMessage);
                    }
                }
                else {
                    handleFailedResponseAN(response);
                }
            }

            @Override
            public void onFailure(Call<AddNurseResponse> call, Throwable t) {
                errorMLiveData.postValue(t.getLocalizedMessage());
            }
        });

    }

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public LiveData<String> getSuccessMLiveData() {
        return successMLiveData;
    }

    public LiveData<String> getErrorMLiveData() {
        return errorMLiveData;
    }

    private void handleFailedResponseAN(Response<AddNurseResponse> response) {
        try {
            String errorResponse = response.errorBody().string();
            System.out.println("****************ERROR MESSAGE************\n"+errorResponse);
            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            errorMLiveData.postValue(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
