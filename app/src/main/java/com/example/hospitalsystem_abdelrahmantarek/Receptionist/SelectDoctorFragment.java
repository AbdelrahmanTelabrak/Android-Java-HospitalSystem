package com.example.hospitalsystem_abdelrahmantarek.Receptionist;

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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.DocListAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Adaptors.ItemClickListener;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.DocNameId;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentSelectDoctorBinding;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectDoctorFragment extends Fragment {

    FragmentSelectDoctorBinding binding;
    NavController navController;
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
        SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
        EmployeeModel employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);
        String token = "Bearer "+employeeModel.getAccessToken();

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

        RetrofitClient.getClient().getDNA("doctor", token).
                enqueue(new Callback<DNAResponse>() {
                    @Override
                    public void onResponse(Call<DNAResponse> call, Response<DNAResponse> response) {
                        if (response.isSuccessful()){
                            if(response.body().isSuccess())
                            {
                                adaptor = new DocListAdaptor(response.body().getData(), itemClickListener);
                                binding.rvDoctorsList.setAdapter(adaptor);
                            }
                            else{
                                String errorMessage = response.body().getMessage();
                                Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            handleFailedResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<DNAResponse> call, Throwable t) {
                        Toast.makeText(view.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

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
    }

    private void handleFailedResponse(Response<DNAResponse> response) {
        try {
            String errorResponse = response.errorBody().string();

            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}