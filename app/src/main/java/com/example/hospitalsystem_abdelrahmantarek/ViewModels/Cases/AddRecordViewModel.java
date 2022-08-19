package com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.AddRecordRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.Models.SimpleResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRecordViewModel extends ViewModel {
    private EmployeeModel employeeModel;
    private MutableLiveData<String> errorMLiveData = new MutableLiveData<>();
    private MutableLiveData<String> successMLiveData = new MutableLiveData<>();

    public void addRecord(Context context, AddRecordRequest request, MultipartBody.Part parts){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);

        RetrofitClient.getClient().addRecord(request.getCaseId(),request.getNote(), "pending",employeeModel.getAccessToken(), parts).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        successMLiveData.postValue("Success");
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
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
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

    public LiveData<String> getSuccessMLiveData() {
        return successMLiveData;
    }

    private void handleFailedResponse(Response<SimpleResponse> response) {
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
