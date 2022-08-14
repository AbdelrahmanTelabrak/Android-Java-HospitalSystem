package com.example.hospitalsystem_abdelrahmantarek.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
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
    CreateTaskViewModel createTaskViewModel;
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

        createTaskViewModel = new ViewModelProvider(this).get(CreateTaskViewModel.class);

        SharedPreferences preferences = getContext().getSharedPreferences("empData", Context.MODE_PRIVATE);
        EmployeeModel employeeModel = new Gson().fromJson(preferences.getString("employee", "null"), EmployeeModel.class);

        getCreateTaskResponse();

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
                binding.etlMCTTaskName.setErrorEnabled(false);
                binding.etlMCTSelectEmp.setErrorEnabled(false);
                binding.etlMCTDescription.setErrorEnabled(false);
                int verify=0;

                if(binding.etiMCTTaskName.getText().toString().isEmpty()){
                    binding.etlMCTTaskName.setError("Task name required");
                    verify+=1;
                }
                if ((binding.etiMCTSelectEmp.getText().toString().isEmpty())){
                    binding.etlMCTSelectEmp.setError("Employee required");
                    verify+=1;
                }
                if (binding.etiMCTDescription.getText().toString().isEmpty()){
                    binding.etlMCTDescription.setError("Description required");
                    verify+=1;
                }
                if(verify==0){
                    CreateTaskRequest request = new CreateTaskRequest(empData.getId(), binding.etiMCTTaskName.getText().toString(),
                            binding.etiMCTDescription.getText().toString(), list);
                    createTaskViewModel.createTask(request, employeeModel.getAccessToken());
                }
            }
        });

        binding.ibMCTBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_managerCreateTaskFragment_to_managerTasksFragment);
            }
        });
    }

    public void getCreateTaskResponse(){
       createTaskViewModel.getCreateSuccessLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
           @Override
           public void onChanged(String s) {
               navController.navigate(R.id.action_managerCreateTaskFragment_to_managerTasksFragment);
               list.clear();
               empData = null;
           }
       });

       createTaskViewModel.getCreateFailedLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
           @Override
           public void onChanged(String s) {
               Toast.makeText(binding.getRoot().getContext(), s, Toast.LENGTH_SHORT).show();
           }
       });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        String toDo = ManagerCreateTaskFragmentArgs.fromBundle(getArguments()).getToDo();
        if (!toDo.equals("null")) {
            if(!toDo.equals("")){
                list.add(toDo);
                adaptor.notifyDataSetChanged();
            }
        }
        if (empData!=null){
            binding.etiMCTSelectEmp.setText(empData.getName());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        list.clear();
        empData = null;
    }
}