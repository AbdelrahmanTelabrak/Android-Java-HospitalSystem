package com.example.hospitalsystem_abdelrahmantarek.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.TasksAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.TaskData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.TasksResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TaskData>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> failedResponseLiveData = new MutableLiveData<>();
    private static final String TAG = "TasksViewModel";

    public void getTasks(String date, Context context){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        EmployeeModel employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);
        Log.i(TAG, "getTasks: token = "+ employeeModel.getAccessToken());
        RetrofitClient.getClient().getTasks(date, employeeModel.getAccessToken()).enqueue(new Callback<TasksResponse>() {
            @Override
            public void onResponse(Call<TasksResponse> call, Response<TasksResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        listMutableLiveData.postValue(response.body().getData());
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        failedResponseLiveData.postValue(errorMessage);
                    }
                }
                else {
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<TasksResponse> call, Throwable t) {
                failedResponseLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public LiveData<ArrayList<TaskData>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public LiveData<String> getFailedResponseLiveData() {
        return failedResponseLiveData;
    }

    private void handleFailedResponse(Response<TasksResponse> response) {
        try {
            String errorResponse = response.errorBody().string();

            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            failedResponseLiveData.postValue(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
