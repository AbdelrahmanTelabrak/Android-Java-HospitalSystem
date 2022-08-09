package com.example.hospitalsystem_abdelrahmantarek.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.ToDoAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.DocNameId;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.CreateTaskRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.CreateTaskResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.TasksResponse;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentManagerCreateTaskBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerCreateTaskFragment extends Fragment {

    FragmentManagerCreateTaskBinding binding;
    NavController navController;
    static ArrayList<String >  list = new ArrayList<>();
    ToDoAdaptor adaptor;
    static DocNameId empData;
    private static final String TAG = "ManagerCreateTaskFragment";

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manager_create_task, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
        EmployeeModel employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);
        String token = "Bearer "+employeeModel.getAccessToken();
        if(empData==null || ManagerCreateTaskFragmentArgs.fromBundle(getArguments()).getEmpIdName()!=null){
            empData = ManagerCreateTaskFragmentArgs.fromBundle(getArguments()).getEmpIdName();
        }
        if(empData != null){
            binding.etiMCTSelectEmp.setText(empData.getName());
        }

        adaptor = new ToDoAdaptor(list);
        binding.rvMCTTodos.setAdapter(adaptor);

        binding.ibMCTAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_managerCreateTaskFragment_to_toDoBottomSheetFragment);
            }
        });

        binding.etiMCTSelectEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_managerCreateTaskFragment_to_managerSelectEmpFragment);
            }
        });

        binding.btnMCTCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTaskRequest request = new CreateTaskRequest(empData.getId(), binding.etiMCTTaskName.getText().toString(),
                        binding.etiMCTDescription.getText().toString(), list);
                RetrofitClient.getClient().createTask(request, token).enqueue(new Callback<CreateTaskResponse>() {
                    @Override
                    public void onResponse(Call<CreateTaskResponse> call, Response<CreateTaskResponse> response) {
                        if (response.isSuccessful()){
                            if(response.body().isSuccess()){
                                navController.navigate(R.id.action_managerCreateTaskFragment_to_managerTasksFragment);
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
                    public void onFailure(Call<CreateTaskResponse> call, Throwable t) {
                        Toast.makeText(view.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String toDo = ManagerCreateTaskFragmentArgs.fromBundle(getArguments()).getToDo();
        if (!toDo.equals("null")) {
            list.add(toDo);
            adaptor.notifyDataSetChanged();
        }
        if (empData!=null){
            binding.etiMCTSelectEmp.setText(empData.getName());
        }
    }

    private void handleFailedResponse(Response<CreateTaskResponse> response) {
        try {
            String errorResponse = response.errorBody().string();
            System.out.println("*********************ERROR****************\n"+errorResponse);
            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}