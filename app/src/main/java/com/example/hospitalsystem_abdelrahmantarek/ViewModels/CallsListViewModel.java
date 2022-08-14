package com.example.hospitalsystem_abdelrahmantarek.ViewModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.RecCallsAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallsListViewModel extends ViewModel {
    MutableLiveData<ArrayList<CallData>> callsListMLiveData = new MutableLiveData<>();
    MutableLiveData<String> errorMLiveData = new MutableLiveData<>();


    public void getCalls(String date, Context context){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        EmployeeModel employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);

        RetrofitClient.getClient().getCalls(date, employeeModel.getAccessToken()).
                enqueue(new Callback<CallsResponse>() {
                    @Override
                    public void onResponse(Call<CallsResponse> call, Response<CallsResponse> response) {
                        if(response.isSuccessful())
                        {
                            if(response.body().isSuccess())
                            {
                                callsListMLiveData.postValue(response.body().getData());
                            }
                            else{
                                String errorMessage = response.body().getMessage();
                                errorMLiveData.postValue(errorMessage);
                            }
                        }
                        else
                        {
                            handleFailedResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<CallsResponse> call, Throwable t) {
                        errorMLiveData.postValue(t.getLocalizedMessage());
                    }
                });
    }

    public LiveData<ArrayList<CallData>> getCallsListMLiveData() {
        return callsListMLiveData;
    }

    public LiveData<String> getErrorMLiveData() {
        return errorMLiveData;
    }

    private void handleFailedResponse(Response<CallsResponse> response) {
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
