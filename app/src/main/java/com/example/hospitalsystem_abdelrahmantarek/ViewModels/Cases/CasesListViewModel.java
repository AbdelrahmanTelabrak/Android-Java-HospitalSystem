package com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseCardData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CasesResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CasesListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<CaseCardData>> casesListLiveData = new MutableLiveData<>();
    private MutableLiveData<String > failedResponseLiveData = new MutableLiveData<>();

    public LiveData<ArrayList<CaseCardData>> getCasesListLiveData() {
        return casesListLiveData;
    }

    public LiveData<String> getFailedResponseLiveData() {
        return failedResponseLiveData;
    }

    public void getCases(Context context){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        EmployeeModel employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);
        RetrofitClient.getClient().getCases(employeeModel.getAccessToken())
                .enqueue(new Callback<CasesResponse>() {
                    @Override
                    public void onResponse(Call<CasesResponse> call, Response<CasesResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body().isSuccess()){
                                casesListLiveData.postValue(response.body().getData());
                            }
                            else{
                                String errorMessage = response.body().getMessage();
                                failedResponseLiveData.postValue(errorMessage);
                            }
                        }
                        else{
                            handleFailedResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<CasesResponse> call, Throwable t) {
                        failedResponseLiveData.postValue(t.getLocalizedMessage());
                    }
                });
    }

    private void handleFailedResponse(Response<CasesResponse> response) {
        try {
            String errorResponse = response.errorBody().string();
            System.out.println("ERROR RESPONSE ****************************\n"+errorResponse);

            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            failedResponseLiveData.postValue(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
