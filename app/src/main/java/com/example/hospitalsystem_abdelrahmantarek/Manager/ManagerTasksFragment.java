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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.RecCallsAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Adaptors.TasksAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.TasksResponse;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentManagerTasksBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerTasksFragment extends Fragment {

    FragmentManagerTasksBinding binding;
    NavController navController;
    TasksAdaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manager_tasks, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date of birth")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();
        SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
        EmployeeModel employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);
        String token = "Bearer "+employeeModel.getAccessToken();

        RetrofitClient.getClient().getTasks("", token).enqueue(new Callback<TasksResponse>() {
            @Override
            public void onResponse(Call<TasksResponse> call, Response<TasksResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        adaptor = new TasksAdaptor(response.body().getData());
                        binding.rvTasksListManager.setAdapter(adaptor);
                    }
                    else{
                        String errorMessage = response.body().getMessage();
                        Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    handleFailedResponse(response);
                }
            }

            @Override
            public void onFailure(Call<TasksResponse> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.ibDateBtnTasksManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(requireActivity().getSupportFragmentManager(), "datePicker");

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis(selection);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate  = format.format(calendar.getTime());
                        binding.etiMngtasksDate.setText(formattedDate);

                        RetrofitClient.getClient().getTasks(formattedDate, token).enqueue(new Callback<TasksResponse>() {
                            @Override
                            public void onResponse(Call<TasksResponse> call, Response<TasksResponse> response) {
                                if (response.isSuccessful()){
                                    if (response.body().isSuccess()){
                                        adaptor = new TasksAdaptor(response.body().getData());
                                        binding.rvTasksListManager.setAdapter(adaptor);
                                    }
                                    else{
                                        String errorMessage = response.body().getMessage();
                                        Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    handleFailedResponse(response);
                                }
                            }

                            @Override
                            public void onFailure(Call<TasksResponse> call, Throwable t) {
                                Toast.makeText(view.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        binding.ibAddTaskManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.ibAddTaskManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_managerTasksFragment_to_managerCreateTaskFragment);
            }
        });

        binding.ibTLManagerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_managerTasksFragment_to_managerMenuFragment);
            }
        });
    }

    private void handleFailedResponse(Response<TasksResponse> response) {
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