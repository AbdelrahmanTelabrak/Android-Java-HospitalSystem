package com.example.hospitalsystem_abdelrahmantarek.Doctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CreateCallResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.ShowCaseResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocCaseDBinding;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DocCaseDFragment extends Fragment {

    FragmentDocCaseDBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_case_d, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        String caseId;
        //if (DocCaseDFragmentArgs.fromBundle(getArguments()).getCaseId() != null){
        caseId= DocCaseDFragmentArgs.fromBundle(getArguments()).getCaseId();

        SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
        EmployeeModel employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);
        String token = "Bearer "+ employeeModel.getAccessToken();

        RetrofitClient.getClient().showCase(Integer.valueOf(caseId), token).enqueue(new Callback<ShowCaseResponse>() {
            @Override
            public void onResponse(Call<ShowCaseResponse> call, Response<ShowCaseResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().isSuccess()){
                        binding.docCDTvName.setText(response.body().getData().getPatientName());
                        binding.docCDTvAge.setText(response.body().getData().getAge());
                        binding.docCDTvPhone.setText(response.body().getData().getPhone());
                        binding.docCDTvDate.setText(response.body().getData().getCreatedAt());
                        binding.docCDTvStatus.setText(response.body().getData().getCaseStatus());
                        binding.docCDTvCaseDescription.setText(response.body().getData().getDescription());
                    }
                    else {
                        String errorMessage = response.body().getMessage();
                        Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ShowCaseResponse> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.docCDBtnAddNurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocCaseDFragmentDirections.ActionDocCaseDFragmentToDocSelectNurseFragment action =
                        DocCaseDFragmentDirections.actionDocCaseDFragmentToDocSelectNurseFragment(Integer.valueOf(caseId));
                navController.navigate(action);
            }
        });
    }

    private void handleFailedResponse(Response<ShowCaseResponse> response) {
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