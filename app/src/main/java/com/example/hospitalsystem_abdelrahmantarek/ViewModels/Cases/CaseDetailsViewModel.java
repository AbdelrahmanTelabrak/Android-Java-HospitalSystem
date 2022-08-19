package com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.ShowCaseResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaseDetailsViewModel extends ViewModel {
    private EmployeeModel employeeModel;
    private MutableLiveData<String> errorMLiveData = new MutableLiveData<>();
    private MutableLiveData<CaseData> caseMLiveData = new MutableLiveData<>();

    public void getCaseDetails(Context context, int caseID){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);

        RetrofitClient.getClient().showCase(caseID, employeeModel.getAccessToken()).enqueue(new Callback<ShowCaseResponse>() {
            @Override
            public void onResponse(Call<ShowCaseResponse> call, Response<ShowCaseResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        caseMLiveData.postValue(response.body().getData());
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
            public void onFailure(Call<ShowCaseResponse> call, Throwable t) {
                errorMLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public LiveData<String> getErrorMLiveData() {
        return errorMLiveData;
    }

    public LiveData<CaseData> getCaseMLiveData() {
        return caseMLiveData;
    }

    private void handleFailedResponse(Response<ShowCaseResponse> response) {
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
