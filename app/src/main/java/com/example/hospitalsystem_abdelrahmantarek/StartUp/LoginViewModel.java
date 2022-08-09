package com.example.hospitalsystem_abdelrahmantarek.StartUp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";
    private MutableLiveData<String > loginSuccessLiveData = new MutableLiveData<>();
    private MutableLiveData<String > loginFailedLiveData = new MutableLiveData<>();
    private MutableLiveData<String > empTypeLiveData = new MutableLiveData<>();

    public void login(LoginRequest loginRequest){
       RetrofitClient.getClient().login(loginRequest).enqueue(new Callback<LoginResponse>() {
           @Override
           public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
               if(response.isSuccessful()){
                   Log.i(TAG, "onResponse: is Successful");
                   handleSuccessResponse(response);
               }
               else {
                   Log.i(TAG, "onResponse: is failed");
                   handleFailedResponse(response);
               }
           }
           @Override
           public void onFailure(Call<LoginResponse> call, Throwable t) {
               loginFailedLiveData.postValue(t.getLocalizedMessage());
           }
       });

    }

    private void handleFailedResponse(Response<LoginResponse> response) {
        try {
            String errorResponse = response.errorBody().string();

            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            loginFailedLiveData.postValue(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSuccessResponse(Response<LoginResponse> response) {
        //save login data in shared preferences
        if(response.body().isSuccess()){
            EmployeeModel employeeModel = new EmployeeModel(response.body().getData().getFirstName(),
                    response.body().getData().getLastName(), response.body().getData().getMobile(), response.body().getData().getEmail(),
                    response.body().getData().getGender(), response.body().getData().getStatus(), response.body().getData().getType(),
                    response.body().getData().getBirthday(), response.body().getData().getAddress(), response.body().getData().getAccessToken());
            String data = new Gson().toJson(employeeModel);
            loginSuccessLiveData.postValue(data);
            empTypeLiveData.postValue(response.body().getData().getType());
        }
        else {
            String errorMessage = response.body().getMessage();
            loginFailedLiveData.postValue(errorMessage);
        }
    }

    public LiveData<String > getLoginSuccess(){
        return loginSuccessLiveData;
    }

    public LiveData<String > getLoginFailed(){
        return loginFailedLiveData;
    }

    public LiveData<String > getEmpType(){
        return empTypeLiveData;
    }
}
