package com.example.hospitalsystem_abdelrahmantarek.Receptionist;

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
import com.example.hospitalsystem_abdelrahmantarek.Adaptors.ItemClickListener;
import com.example.hospitalsystem_abdelrahmantarek.Models.DocNameId;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Employees.SelectEmpViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentSelectDoctorBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectDoctorFragment extends Fragment {

    FragmentSelectDoctorBinding binding;
    NavController navController;
    SelectEmpViewModel selectEmpViewModel;
    DocListAdaptor adaptor;
    ItemClickListener itemClickListener;
    DocNameId docNameId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_doctor, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        selectEmpViewModel = new ViewModelProvider(this).get(SelectEmpViewModel.class);

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(String name, int id) {
                // Notify adapter
                binding.rvDoctorsList.post(new Runnable() {
                    @Override
                    public void run() {
                        adaptor.notifyDataSetChanged();
                    }
                });
                Toast.makeText(view.getContext(), name, Toast.LENGTH_SHORT).show();
                docNameId = new DocNameId(name, id);
            }
        };
        selectEmpViewModel.getTypeEmp(requireContext(), "doctor");

        binding.etiSDocSearch.addTextChangedListener(new TextWatcher() {
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

        binding.btnSelectSelectDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(docNameId != null) {
                    SelectDoctorFragmentDirections.ActionSelectDoctorFragmentToCreateCallFragment action =
                            SelectDoctorFragmentDirections.actionSelectDoctorFragmentToCreateCallFragment().setDocData(docNameId);
                    navController.navigate(action);
                }
                else{
                    Toast.makeText(view.getContext(), "Select a doctor", Toast.LENGTH_SHORT).show();
                }

            }
        });

        typeEmpObserver();
    }

    public void typeEmpObserver(){
        selectEmpViewModel.getListMLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<DNAData>>() {
            @Override
            public void onChanged(ArrayList<DNAData> data) {
                adaptor = new DocListAdaptor(data, itemClickListener);
                binding.rvDoctorsList.setAdapter(adaptor);
            }
        });
        selectEmpViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}