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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CreateCallRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CreateCallResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.DocNameId;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Calls.CreateCallViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentCreateCallBinding;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateCallFragment extends Fragment {
    FragmentCreateCallBinding binding;
    NavController navController;
    CreateCallViewModel createCallViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_call, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        createCallViewModel = new ViewModelProvider(this).get(CreateCallViewModel.class);

        DocNameId docNameId = CreateCallFragmentArgs.fromBundle(getArguments()).getDocData();

        if(CreateCallFragmentArgs.fromBundle(getArguments()).getDocData() != null){
            assert docNameId != null;
            binding.etiCCSelectDoctor.setText(docNameId.getName());
        }

        binding.etiCCSelectDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_createCallFragment_to_selectDoctorFragment);
            }
        });

        binding.btnCreateCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCallRequest callRequest = new CreateCallRequest(binding.etiCCPatientName.getText().toString(), Integer.valueOf(CreateCallFragmentArgs.fromBundle(getArguments()).getDocData().getId()),
                        binding.etiCCAge.getText().toString(), binding.etiCCMobile.getText().toString(),
                        binding.etiCCDescription.getText().toString());
                createCallViewModel.createCall(requireContext(), callRequest);
            }
        });

        createCallObserver();
    }

    public void createCallObserver(){
        createCallViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                navController.navigate(R.id.action_createCallFragment_to_PCompletedFragment);
            }
        });

        createCallViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}