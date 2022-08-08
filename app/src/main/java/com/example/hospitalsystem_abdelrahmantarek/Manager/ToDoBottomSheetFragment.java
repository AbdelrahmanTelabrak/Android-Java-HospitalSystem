package com.example.hospitalsystem_abdelrahmantarek.Manager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentToDoBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ToDoBottomSheetFragment extends BottomSheetDialogFragment {

    FragmentToDoBottomSheetBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_to_do_bottom_sheet, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = NavHostFragment.findNavController(this);

        binding.bsfBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoBottomSheetFragmentDirections.ActionToDoBottomSheetFragmentToManagerCreateTaskFragment action =
                        ToDoBottomSheetFragmentDirections.actionToDoBottomSheetFragmentToManagerCreateTaskFragment().setToDo(binding.etiTDBSDetails.getText().toString());
                navController.navigate(action);
            }
        });

    }
}