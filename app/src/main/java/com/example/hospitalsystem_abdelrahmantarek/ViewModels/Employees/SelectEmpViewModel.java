package com.example.hospitalsystem_abdelrahmantarek.ViewModels.Employees;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectEmpViewModel extends ViewModel {
    private EmployeeModel employeeModel;
    private MutableLiveData<ArrayList<DNAData>> listMLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMLiveData = new MutableLiveData<>();

    public void getTypeEmp(Context context, String empType){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);
        RetrofitClient.getClient().getDNA(empType, employeeModel.getAccessToken()).
                enqueue(new Callback<DNAResponse>() {
                    @Override
                    public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                        if (response.isSuccessful()){
                            if(response.body().isSuccess())
                            {
                                listMLiveData.postValue(response.body().getData());
                            }
                            else{
                                String errorMessage = response.body().getMessage();
                                errorMLiveData.postValue(errorMessage);
                            }
                        }
                        else{
                            handleFailedResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<DNAResponse> call, Throwable t) {
                        errorMLiveData.postValue(t.getLocalizedMessage());
                    }
                });

    }

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public LiveData<ArrayList<DNAData>> getListMLiveData() {
        return listMLiveData;
    }

    public LiveData<String> getErrorMLiveData() {
        return errorMLiveData;
    }

    private void handleFailedResponse(Response<DNAResponse> response) {
        try {
            String errorResponse = response.errorBody().string();

            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            errorMLiveData.postValue(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
