package com.example.hospitalsystem_abdelrahmantarek.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
    DocNameId empData;
    NavController navController;
    AllEmployeesViewModel allEmployeesViewModel;
    ItemClickListener itemClickListener;
    DocListAdaptor adaptor;

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

        allEmployeesViewModel = new ViewModelProvider(this).get(AllEmployeesViewModel.class);

        SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
        EmployeeModel employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);

        binding.mngEmpListBtnAll.setChecked(true);
        allEmployeesViewModel.getEmployees(employeeModel.getAccessToken());
        empListObserver();

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

                adaptor = new DocListAdaptor(allEmployeesViewModel.getAllEmployeesList(), itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnDoc.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(allEmployeesViewModel.getDoctorsList(), itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnNurse.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(allEmployeesViewModel.getNursesList(), itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnHr.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(allEmployeesViewModel.getHrsList(), itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnManager.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(allEmployeesViewModel.getManagersList(), itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnRec.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(allEmployeesViewModel.getReceptionistsList(), itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
            }
        });

        binding.mngEmpListBtnAnalysis.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                adaptor = new DocListAdaptor(allEmployeesViewModel.getAnalysisList(), itemClickListener);
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

    public void empListObserver(){
        allEmployeesViewModel.getAllEmpList().observe(getViewLifecycleOwner(), new Observer<ArrayList<DNAData>>() {
            @Override
            public void onChanged(ArrayList<DNAData> data) {
                adaptor = new DocListAdaptor(data, itemClickListener);
                binding.rvMSEEmpList.setAdapter(adaptor);
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