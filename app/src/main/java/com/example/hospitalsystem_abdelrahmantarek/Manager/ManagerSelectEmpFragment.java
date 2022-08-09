package com.example.hospitalsystem_abdelrahmantarek.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.DocListAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Adaptors.EmployeeListAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Adaptors.ItemClickListener;
import com.example.hospitalsystem_abdelrahmantarek.Models.DocNameId;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentManagerSelectEmpBinding;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerSelectEmpFragment extends Fragment {

    FragmentManagerSelectEmpBinding binding;
    EmployeeModel employeeModel;
    DocNameId empData;
    NavController navController;
    ItemClickListener itemClickListener;
    DocListAdaptor adaptor;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manager_select_emp, container, false);
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

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(String name, int id) {
                // Notify adapter
                binding.rvMSEEmpList.post(new Runnable() {
                    @Override
                    public void run() {
                        adaptor.notifyDataSetChanged();
                    }
                });
                Toast.makeText(view.getContext(), name, Toast.LENGTH_SHORT).show();
                empData = new DocNameId(name, id);
            }
        };

        binding.mngEmpListBtnAll.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
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
                adaptor = new DocListAdaptor(AllEmployeesList, itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnDoc.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(doctorsList, itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnNurse.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(NursesList, itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnHr.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(hrsList, itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnManager.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(managersList, itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnRec.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(receptionistsList, itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnAnalysis.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(analysisList, itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.etiMSESearch.addTextChangedListener(new TextWatcher() {
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

        binding.btnMSESEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagerSelectEmpFragmentDirections.ActionManagerSelectEmpFragmentToManagerCreateTaskFragment action =
                        ManagerSelectEmpFragmentDirections.actionManagerSelectEmpFragmentToManagerCreateTaskFragment().setEmpIdName(empData);
                navController.navigate(action);
            }
        });

        binding.ibMSEBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_managerSelectEmpFragment_to_managerCreateTaskFragment);
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