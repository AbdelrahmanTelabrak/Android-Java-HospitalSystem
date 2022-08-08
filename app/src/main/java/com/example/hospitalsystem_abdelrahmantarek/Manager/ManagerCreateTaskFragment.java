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

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.ToDoAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentManagerCreateTaskBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ManagerCreateTaskFragment extends Fragment {

    FragmentManagerCreateTaskBinding binding;
    NavController navController;
    static ArrayList<String >  list = new ArrayList<>();
    ToDoAdaptor adaptor;
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
        adaptor = new ToDoAdaptor(list);
        binding.rvMCTTodos.setAdapter(adaptor);

        binding.ibMCTAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_managerCreateTaskFragment_to_toDoBottomSheetFragment);
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
    }
}