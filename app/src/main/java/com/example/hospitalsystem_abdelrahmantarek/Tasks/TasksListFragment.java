package com.example.hospitalsystem_abdelrahmantarek.Tasks;

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

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.TasksAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Tasks.TasksViewModel;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.TaskData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentTasksListBinding;

import java.util.ArrayList;


public class TasksListFragment extends Fragment {

    FragmentTasksListBinding binding;
    NavController navController;
    TasksViewModel tasksViewModel;
    TasksAdaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        navController.popBackStack(R.id.taskDetailsFragment, true);
        String date = "";
        if(!TasksListFragmentArgs.fromBundle(getArguments()).getDate().equals("null")) {
            date = TasksListFragmentArgs.fromBundle(getArguments()).getDate();
            binding.etiTaskDateTasksList.setText(date);
        }

        tasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        tasksViewModel.getTasks(date, binding.getRoot().getContext());
        tasksObserver();

        binding.ibTLChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_tasksListFragment_to_dateBottomSheetFragment);
            }
        });
    }

    public void tasksObserver(){
        tasksViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<TaskData>>() {
            @Override
            public void onChanged(ArrayList<TaskData> taskData) {
                adaptor = new TasksAdaptor(taskData, tasksViewModel.getEmployeeModel().getType());
                binding.rvTasksList.setAdapter(adaptor);
            }
        });
        tasksViewModel.getFailedResponseLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(binding.getRoot().getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}