package com.example.hospitalsystem_abdelrahmantarek.Manager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.CheckToDoAdapter;
import com.example.hospitalsystem_abdelrahmantarek.Adaptors.EmployeeReplyAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.TaskDetails;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.Tasks.TaskDetailsFragmentArgs;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Tasks.ExecuteTaskViewModel;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Tasks.TaskDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentManagerTaskDetailsBinding;

import java.util.ArrayList;

public class ManagerTaskDetailsFragment extends Fragment {
    FragmentManagerTaskDetailsBinding binding;
    NavController navController;
    TaskDetailsViewModel taskDetailsViewModel;
    ExecuteTaskViewModel executeTaskViewModel;
    CheckToDoAdapter adapter;
    EmployeeReplyAdaptor employeeReplyAdaptor;
    int taskId;
    int todoCount;
    String status, empType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manager_task_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        taskDetailsViewModel = new ViewModelProvider(this).get(TaskDetailsViewModel.class);
        executeTaskViewModel = new ViewModelProvider(this).get(ExecuteTaskViewModel.class);

        taskId = ManagerTaskDetailsFragmentArgs.fromBundle(getArguments()).getTaskId();

        taskDetailsViewModel.showTask(requireContext(), taskId);
        taskDetailsObserver();

        binding.btnTDExecution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("pending")){
                    executeTaskViewModel.executeTaskManager(requireContext(), taskId);
                }
                else
                    Toast.makeText(requireContext(), "This task is already executed", Toast.LENGTH_SHORT).show();
            }
        });
        executionObserver();
    }

    private void executionObserver(){
        executeTaskViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                navController.navigate(R.id.action_taskDetailsFragment_to_tasksListFragment);
            }
        });

        executeTaskViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void taskDetailsObserver(){
        taskDetailsViewModel.getTodoListMLiveData().observe(getViewLifecycleOwner(), new Observer<TaskDetails>() {
            @Override
            public void onChanged(TaskDetails taskDetails) {
                todoCount = taskDetails.getToDo().size();
                status = taskDetails.getStatus();
                empType = taskDetailsViewModel.getEmployeeModel().getType();
                binding.tvTDMngName.setText("Manager Name");
                binding.tvTDTaskDescription.setText(taskDetails.getDescription());
                binding.tvRDReportDate.setText(taskDetails.getCreatedAt());
                binding.tvTDTaskName.setText(taskDetails.getTaskName());
                adapter = new CheckToDoAdapter(taskDetails.getToDo(), status, empType);
                binding.rvTDCheckTodo.setAdapter(adapter);
                if(taskDetails.getNote() != null){
                    ArrayList<TaskDetails> reply = new ArrayList<>();
                    reply.add(taskDetails);
                    employeeReplyAdaptor = new EmployeeReplyAdaptor(reply);
                    binding.rvMTDEmpReply.setAdapter(employeeReplyAdaptor);
                }
                else
                    binding.imageView17.setImageResource(R.color.white);
            }
        });

        taskDetailsViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}