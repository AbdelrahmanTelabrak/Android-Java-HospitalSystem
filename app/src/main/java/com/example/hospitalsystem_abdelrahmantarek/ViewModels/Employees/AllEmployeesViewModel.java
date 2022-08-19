package com.example.hospitalsystem_abdelrahmantarek.ViewModels.Employees;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllEmployeesViewModel extends ViewModel {
    private static final String TAG = "AllEmployeesViewModel";
    private EmployeeModel employeeModel;
    private MutableLiveData<ArrayList<DNAData>> allListLiveData = new MutableLiveData<>();
    private MutableLiveData<String > errorLiveData = new MutableLiveData<>();
    private MutableLiveData<String > empTypeLiveData = new MutableLiveData<>();
    private ArrayList<DNAData> allEmployeesList = new ArrayList<>();
    private ArrayList<DNAData> doctorsList = new ArrayList<>();
    private ArrayList<DNAData> nursesList = new ArrayList<>();
    private ArrayList<DNAData> hrsList = new ArrayList<>();
    private ArrayList<DNAData> managersList = new ArrayList<>();
    private ArrayList<DNAData> receptionistsList = new ArrayList<>();
    private ArrayList<DNAData> analysisList = new ArrayList<>();

    public void getEmployees(Context context){
        SharedPreferences preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);
        empTypeLiveData.postValue(employeeModel.getType());
        getDoctors(doctorsList, employeeModel.getAccessToken(), allEmployeesList);
        getNurses(nursesList, employeeModel.getAccessToken(), allEmployeesList);
        getReceptionists(receptionistsList, employeeModel.getAccessToken(), allEmployeesList);
        getAnalysisEmps(analysisList, employeeModel.getAccessToken(), allEmployeesList);
        getHrs(hrsList, employeeModel.getAccessToken(), allEmployeesList);
        getManagers(managersList, employeeModel.getAccessToken(), allEmployeesList);

//        allEmployeesList.addAll(doctorsList);
//        allEmployeesList.addAll(nursesList);
//        alEmployeesList.addAll(receptionistsList);
//        allEmployeesList.addAll(analysisList);
//        allEmployeesList.addAll(hrsList);
//        allEmployeesList.addAll(managersList);
//        Collections.shuffle(AllEmployeesList);
        Log.i(TAG, "getEmployees: size== " +allEmployeesList.size());
        Log.i(TAG, "getEmployees: docsize== " +doctorsList.size());

        //allListLiveData.postValue(AllEmployeesList);
    }

    public ArrayList<DNAData> getAllEmployeesList() {
        return allEmployeesList;
    }

    public ArrayList<DNAData> getDoctorsList() {
        return doctorsList;
    }

    public ArrayList<DNAData> getNursesList() {
        return nursesList;
    }

    public ArrayList<DNAData> getHrsList() {
        return hrsList;
    }

    public ArrayList<DNAData> getManagersList() {
        return managersList;
    }

    public ArrayList<DNAData> getReceptionistsList() {
        return receptionistsList;
    }

    public ArrayList<DNAData> getAnalysisList() {
        return analysisList;
    }

    public LiveData<ArrayList<DNAData>> getAllEmpList(){
        return allListLiveData;
    }

    public LiveData<String > getError(){
        return errorLiveData;
    }

    public LiveData<String> getEmpTypeLiveData() {
        return empTypeLiveData;
    }

    public void getDoctors(ArrayList<DNAData> data, String token, ArrayList<DNAData> allEmployeesList){
        RetrofitClient.getClient().getDNA("doctor", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                        allEmployeesList.addAll(response.body().getData());
                        allListLiveData.postValue(allEmployeesList);
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        errorLiveData.postValue(errorMessage);
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                errorLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public void getNurses(ArrayList<DNAData> data, String token, ArrayList<DNAData> allEmployeesList){
        RetrofitClient.getClient().getDNA("nurse", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                        allEmployeesList.addAll(response.body().getData());
                        allListLiveData.postValue(allEmployeesList);
                        Collections.shuffle(allEmployeesList);
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        errorLiveData.postValue(errorMessage);
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                errorLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public void getReceptionists(ArrayList<DNAData> data, String token, ArrayList<DNAData> allEmployeesList){
        RetrofitClient.getClient().getDNA("receptionist", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                        allEmployeesList.addAll(response.body().getData());
                        allListLiveData.postValue(allEmployeesList);
                        Collections.shuffle(allEmployeesList);
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        errorLiveData.postValue(errorMessage);
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                errorLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public void getAnalysisEmps(ArrayList<DNAData> data, String token, ArrayList<DNAData> allEmployeesList){
        RetrofitClient.getClient().getDNA("analysis", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                        allEmployeesList.addAll(response.body().getData());
                        allListLiveData.postValue(allEmployeesList);
                        Collections.shuffle(allEmployeesList);
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        errorLiveData.postValue(errorMessage);
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                errorLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public void getHrs(ArrayList<DNAData> data, String token, ArrayList<DNAData> allEmployeesList){
        RetrofitClient.getClient().getDNA("hr", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                        allEmployeesList.addAll(response.body().getData());
                        allListLiveData.postValue(allEmployeesList);
                        Collections.shuffle(allEmployeesList);
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        errorLiveData.postValue(errorMessage);
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                errorLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    public void getManagers(ArrayList<DNAData> data, String token, ArrayList<DNAData> allEmployeesList){
        RetrofitClient.getClient().getDNA("manger", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                        allEmployeesList.addAll(response.body().getData());
                        allListLiveData.postValue(allEmployeesList);
                        Collections.shuffle(allEmployeesList);
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        errorLiveData.postValue(errorMessage);
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                errorLiveData.postValue(t.getLocalizedMessage());
            }
        });
    }

    private void handleFailedResponse(Response<DNAResponse> response) {
        try {
            String errorResponse = response.errorBody().string();
            System.out.println("*****************ERROR*****************\n"+errorResponse);
            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            errorLiveData.postValue(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
