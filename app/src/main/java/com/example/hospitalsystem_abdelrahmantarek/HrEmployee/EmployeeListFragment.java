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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
    NavController navController;
    AllEmployeesViewModel allEmployeesViewModel;
    EmployeeListAdaptor adaptor;

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

        allEmployeesViewModel = new ViewModelProvider(this).get(AllEmployeesViewModel.class);

        SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
        EmployeeModel employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);

        binding.hrEmpListBtnAll.setChecked(true);
        allEmployeesViewModel.getEmployees(employeeModel.getAccessToken());
        empListObserver();

        binding.btnAddEmpEmpList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_employeeListFragment_to_hrAddUserFragment);
            }
        });

        binding.hrEmpListBtnAll.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(allEmployeesViewModel.getAllEmployeesList());
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnDoc.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(allEmployeesViewModel.getDoctorsList());
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnNurse.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(allEmployeesViewModel.getNursesList());
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnHr.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(allEmployeesViewModel.getHrsList());
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnManager.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(allEmployeesViewModel.getManagersList());
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnRec.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(allEmployeesViewModel.getReceptionistsList());
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        binding.hrEmpListBtnAnalysis.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new EmployeeListAdaptor(allEmployeesViewModel.getAnalysisList());
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

    public void empListObserver(){
        allEmployeesViewModel.getAllEmpList().observe(getViewLifecycleOwner(), new Observer<ArrayList<DNAData>>() {
            @Override
            public void onChanged(ArrayList<DNAData> data) {
                adaptor = new EmployeeListAdaptor(data);
                binding.rvELEmpList.setAdapter(adaptor);
            }
        });

        allEmployeesViewModel.getError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(binding.getRoot().getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}