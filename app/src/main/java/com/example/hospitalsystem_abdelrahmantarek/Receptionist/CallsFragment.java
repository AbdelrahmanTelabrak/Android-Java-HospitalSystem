package com.example.hospitalsystem_abdelrahmantarek.Receptionist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.RecCallsAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.DateBottomSheetFragment;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.Tasks.TasksListFragmentArgs;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.CallsListViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentCallsBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallsFragment extends Fragment {
    FragmentCallsBinding binding;
    NavController navController;
    CallsListViewModel callsListViewModel;
    RecCallsAdaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calls, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        String date = "";
        if(!CallsFragmentArgs.fromBundle(getArguments()).getDate().equals("null")) {
            date = CallsFragmentArgs.fromBundle(getArguments()).getDate();
            binding.etiRecCallsDate.setText(date);
        }

        callsListViewModel = new ViewModelProvider(this).get(CallsListViewModel.class);
        callsListViewModel.getCalls(date, binding.getRoot().getContext());
        callsObserver();

        binding.ibDateBtnCalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_callsFragment_to_recDateBottomSheetFragment);
            }
        });

        binding.ibAddCallReceptionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_callsFragment_to_createCallFragment);
            }
        });
    }

    private  void callsObserver(){
        callsListViewModel.getCallsListMLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<CallData>>() {
            @Override
            public void onChanged(ArrayList<CallData> callData) {
                adaptor = new RecCallsAdaptor(callData);
                binding.rvCallsListCalls.setAdapter(adaptor);
            }
        });

        callsListViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(binding.getRoot().getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}