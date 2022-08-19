package com.example.hospitalsystem_abdelrahmantarek.Doctor;

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

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.DocCallsListAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Calls.CallsListViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocCallsListBinding;

import java.util.ArrayList;


public class DocCallsListFragment extends Fragment {

    FragmentDocCallsListBinding binding;
    NavController navController;
    CallsListViewModel callsListViewModel;
    DocCallsListAdaptor dclAdaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_calls_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        callsListViewModel = new ViewModelProvider(this).get(CallsListViewModel.class);

        callsListViewModel.getCalls("", requireContext());
        callsObserver();

        binding.ibDclBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_docCallsListFragment_to_doctorMenuFragment);
            }
        });
    }

    private void callsObserver(){
        callsListViewModel.getCallsListMLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<CallData>>() {
            @Override
            public void onChanged(ArrayList<CallData> callData) {
                dclAdaptor = new DocCallsListAdaptor(callData);
                binding.rvDclList.setAdapter(dclAdaptor);
            }
        });
    }
}