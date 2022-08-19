package com.example.hospitalsystem_abdelrahmantarek.ViewModels.Reports;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.CreateReportRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.CreateReportResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.ReportCardData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.ReportsListResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsViewModel extends ViewModel {
    static EmployeeModel employeeModel;
    private MutableLiveData<ArrayList<ReportCardData>> reportsListMLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMLiveData = new MutableLiveData<>();
    private MutableLiveData<String> successMLiveData = new MutableLiveData<>();

    public void getReports(String date, Context context){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);

        RetrofitClient.getClient().getReports(date, employeeModel.getAccessToken()).enqueue(new Callback<ReportsListResponse>() {
            @Override
            public void onResponse(Call<ReportsListResponse> call, Response<ReportsListResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        reportsListMLiveData.postValue(response.body().getData());
                    }
                    else {
                        String errorMessage = response.body().getMessage();
                        errorMLiveData.postValue(errorMessage);
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ReportsListResponse> call, Throwable t) {
                errorMLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public void createReport(CreateReportRequest request){
        RetrofitClient.getClient().createReport(request, employeeModel.getAccessToken()).enqueue(new Callback<CreateReportResponse>() {
            @Override
            public void onResponse(Call<CreateReportResponse> call, Response<CreateReportResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        successMLiveData.postValue("Success");
                    }
                    else {
                        String errorMessage = response.body().getMessage();
                        errorMLiveData.postValue(errorMessage);
                    }
                }
                else{
                    handleCreateFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<CreateReportResponse> call, Throwable t) {
                errorMLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }


    public LiveData<ArrayList<ReportCardData>> getReportsListMLiveData() {
        return reportsListMLiveData;
    }

    public LiveData<String> getErrorMLiveData() {
        return errorMLiveData;
    }

    public LiveData<String> getSuccessMLiveData() {
        return successMLiveData;
    }

    private void handleFailedResponse(Response<ReportsListResponse> response) {
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

    private void handleCreateFailedResponse(Response<CreateReportResponse> response) {
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
