package com.example.hospitalsystem_abdelrahmantarek.HrEmployee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.EmployeeListAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.MainActivity;
import com.example.hospitalsystem_abdelrahmantarek.Manager.AllEmployeesViewModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.SplashScreenActivity;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentEmployeeListBinding;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmployeeListFragment extends Fragment {

    private static final String TAG = "EmployeeListFragment";
    FragmentEmployeeListBinding binding;
    EmployeeModel employeeModel;
    NavController navController;
    AllEmployeesViewModel allEmployeesViewModel;
    EmployeeListAdaptor adaptor;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { 
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
        employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);
        token = "Bearer "+employeeModel.getAccessToken();
        ArrayList<DNAData> AllEmployeesList = new ArrayList<>();
        ArrayList<DNAData> doctorsList = new ArrayList<>();
        ArrayList<DNAData> NursesList = new ArrayList<>();
        ArrayList<DNAData> hrsList = new ArrayList<>();
        ArrayList<DNAData> managersList = new ArrayList<>();
        ArrayList<DNAData> receptionistsList = new ArrayList<>();
        ArrayList<DNAData> analysisList = new ArrayList<>();

        getDoctors(doctorsList);
        getNurses(NursesList);
        getReceptionists(receptionistsList);
        getAnalysisEmps(analysisList);
        getHrs(hrsList);
        getManagers(managersList);

        binding.btnAddEmpEmpList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_employeeListFragment_to_hrAddUserFragment);
            }
        });

        binding.hrEmpListBtnAll.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                if(AllEmployeesList.size()==0){
                    AllEmployeesList.addAll(doctorsList);
                    AllEmployeesList.addAll(NursesList);
                    AllEmployeesList.addAll(receptionistsList);
                    AllEmployeesList.addAll(analysisList);
                    AllEmployeesList.addAll(hrsList);
                    AllEmployeesList.addAll(managersList);
                    Collections.shuffle(AllEmployeesList);
                }
                adaptor = new EmployeeListAdaptor(AllEmployeesList);
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnDoc.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(doctorsList);
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnNurse.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(NursesList);
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnHr.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(hrsList);
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnManager.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(managersList);
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnRec.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(receptionistsList);
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnAnalysis.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(analysisList);
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.etiELSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptor.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.ibELBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(employeeModel.getType().trim().toLowerCase().equals("manager")||employeeModel.getType().trim().toLowerCase().equals("manger")){
                    navController.navigate(R.id.action_employeeListFragment_to_managerMenuFragment);
                }
                else if(employeeModel.getType().trim().toLowerCase().equals("hr")){
                    navController.navigate(R.id.action_employeeListFragment_to_mainMenuHrFragment);
                }
            }
        });
    }

    public void getDoctors(ArrayList<DNAData> data){
        RetrofitClient.getClient().getDNA("doctor", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        Toast.makeText(binding.getRoot().getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNurses(ArrayList<DNAData> data){
        RetrofitClient.getClient().getDNA("nurse", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        Toast.makeText(binding.getRoot().getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getReceptionists(ArrayList<DNAData> data){
        RetrofitClient.getClient().getDNA("receptionist", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        Toast.makeText(binding.getRoot().getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAnalysisEmps(ArrayList<DNAData> data){
        RetrofitClient.getClient().getDNA("analysis", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        Toast.makeText(binding.getRoot().getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getHrs(ArrayList<DNAData> data){
        RetrofitClient.getClient().getDNA("hr", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        Toast.makeText(binding.getRoot().getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getManagers(ArrayList<DNAData> data){
        RetrofitClient.getClient().getDNA("manger", token).enqueue(new Callback<DNAResponse>() {
            @Override
            public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        data.addAll(response.body().getData());
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        Toast.makeText(binding.getRoot().getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<DNAResponse> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}