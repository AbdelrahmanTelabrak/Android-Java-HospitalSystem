package com.example.hospitalsystem_abdelrahmantarek.Manager;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.CreateTaskRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.CreateTaskResponse;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTaskViewModel extends ViewModel {
    private MutableLiveData<String> createSuccessLiveData = new MutableLiveData<>();
    private MutableLiveData<String> createFailedLiveData = new MutableLiveData<>();

    public void createTask(CreateTaskRequest request, String token){
        RetrofitClient.getClient().createTask(request, token).enqueue(new Callback<CreateTaskResponse>() {
            @Override
            public void onResponse(Call<CreateTaskResponse> call, Response<CreateTaskResponse> response) {
                if (response.isSuccessful()){
                    if(response.body().isSuccess()){
                        createSuccessLiveData.postValue("Success");
                    }
                    else {
                        String errorMessage = response.body().getMessage();
                        createFailedLiveData.postValue(errorMessage);
                    }
                }
                else {
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<CreateTaskResponse> call, Throwable t) {
                createFailedLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public LiveData<String> getCreateSuccessLiveData() {
        return createSuccessLiveData;
    }

    public LiveData<String> getCreateFailedLiveData() {
        return createFailedLiveData;
    }

    private void handleFailedResponse(Response<CreateTaskResponse> response) {
        try {
            String errorResponse = response.errorBody().string();
            System.out.println("*********************ERROR****************\n"+errorResponse);
            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            createFailedLiveData.postValue(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
