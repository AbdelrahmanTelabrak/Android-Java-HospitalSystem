package com.example.hospitalsystem_abdelrahmantarek.ViewModels.Reports;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.AnswerReportResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.ExcuteReportResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.ReportDetailsData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.ShowReportResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDetailsViewModel extends ViewModel {
    private MutableLiveData<ReportDetailsData> reportDetailsMLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMLiveData = new MutableLiveData<>();
    private MutableLiveData<String> successMLiveData = new MutableLiveData<>();
    private static EmployeeModel employeeModel;
    private static final String TAG = "ReportDetailsViewModel";

    public void showReport(int id, Context context){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);
        Log.i(TAG, "showReport: "+employeeModel.toString());
        Log.i(TAG, "showReport: id == "+id);
        RetrofitClient.getClient().showReport(id, employeeModel.getAccessToken()).enqueue(new Callback<ShowReportResponse>() {
            @Override
            public void onResponse(Call<ShowReportResponse> call, Response<ShowReportResponse> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: ");
                    if(response.body().isSuccess()){
                        reportDetailsMLiveData.postValue(response.body().getData());
                    }
                    else {
                        String errorMessage = response.body().getMessage();
                        errorMLiveData.postValue(errorMessage);
                    }
                }
                else {
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ShowReportResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+ t.getLocalizedMessage());
                errorMLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public void deleteReport(int id){
        RetrofitClient.getClient().deleteReport(id, employeeModel.getAccessToken()).enqueue(new Callback<ExcuteReportResponse>() {
            @Override
            public void onResponse(Call<ExcuteReportResponse> call, Response<ExcuteReportResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().isSuccess()){
                        successMLiveData.postValue("Success");
                    }
                    else {
                        String errorMessage = response.body().getMessage();
                        errorMLiveData.postValue(errorMessage);
                    }
                }
                else {
                    handleDeleteFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ExcuteReportResponse> call, Throwable t) {
                errorMLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public void sendReply(int id, String answer){
        RetrofitClient.getClient().answerReport(id, answer, employeeModel.getAccessToken()).enqueue(new Callback<AnswerReportResponse>() {
            @Override
            public void onResponse(Call<AnswerReportResponse> call, Response<AnswerReportResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        successMLiveData.postValue("Answer Success");
                    }
                    else {
                        String errorMessage = response.body().getMessage();
                        errorMLiveData.postValue(errorMessage);
                    }
                }
                else {
                    handleAnswerFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<AnswerReportResponse> call, Throwable t) {
                errorMLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public LiveData<ReportDetailsData> getReportDetailsMLiveData() {
        return reportDetailsMLiveData;
    }

    public LiveData<String> getErrorMLiveData() {
        return errorMLiveData;
    }

    public LiveData<String> getSuccessMLiveData() {
        return successMLiveData;
    }

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    private void handleFailedResponse(Response<ShowReportResponse> response) {
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

    private void handleDeleteFailedResponse(Response<ExcuteReportResponse> response) {
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

    private void handleAnswerFailedResponse(Response<AnswerReportResponse> response) {
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
