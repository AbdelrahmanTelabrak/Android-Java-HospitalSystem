package com.example.hospitalsystem_abdelrahmantarek.ViewModels.Tasks;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.ShowTaskResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.TaskDetails;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskDetailsViewModel extends ViewModel {
    private EmployeeModel employeeModel;
    private MutableLiveData<String> errorMLiveData = new MutableLiveData<>();
    private MutableLiveData<TaskDetails> todoListMLiveData = new MutableLiveData<>();

    public void showTask(Context context, int taskId){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);

        RetrofitClient.getClient().showTask(taskId, employeeModel.getAccessToken()).enqueue(new Callback<ShowTaskResponse>() {
            @Override
            public void onResponse(Call<ShowTaskResponse> call, Response<ShowTaskResponse> response) {
                if (response.isSuccessful()){
                    if(response.body().isSuccess()){
                        todoListMLiveData.postValue(response.body().getData());
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
            public void onFailure(Call<ShowTaskResponse> call, Throwable t) {
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

    public LiveData<TaskDetails> getTodoListMLiveData() {
        return todoListMLiveData;
    }

    private void handleFailedResponse(Response<ShowTaskResponse> response) {
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
