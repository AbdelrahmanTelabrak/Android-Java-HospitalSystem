package com.example.hospitalsystem_abdelrahmantarek.Doctor;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentRequestBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class RequestBottomSheetFragment extends BottomSheetDialogFragment {
    FragmentRequestBottomSheetBinding binding;
    NavController navController;
    String choice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_bottom_sheet, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        choice = "";

        binding.clRBSRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.clRBSRecord.setBackgroundResource(R.drawable.shape_active_border);
                binding.ivRequestRecord.setImageResource(R.drawable.ic_active_request_record);
                binding.tvRequestRecord.setTextColor(Color.parseColor("#22C7B8"));
                binding.clRBSMeasurement.setBackgroundResource(R.drawable.shape_border);
                binding.ivRequestMeasurement.setImageResource(R.drawable.ic_request_measurement);
                binding.tvRequestMeasurement.setTextColor(Color.parseColor("#AEAEAE"));
                choice = "record";
            }
        });

        binding.clRBSMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.clRBSRecord.setBackgroundResource(R.drawable.shape_border);
                binding.ivRequestRecord.setImageResource(R.drawable.ic_request_record);
                binding.tvRequestRecord.setTextColor(Color.parseColor("#AEAEAE"));
                binding.clRBSMeasurement.setBackgroundResource(R.drawable.shape_active_border);
                binding.ivRequestMeasurement.setImageResource(R.drawable.ic_active_request_measurment);
                binding.tvRequestMeasurement.setTextColor(Color.parseColor("#22C7B8"));
                choice = "measurement";
            }
        });

        binding.btnRBSRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choice.equals("record")){
                    navController.navigate(R.id.action_requestBottomSheetFragment_to_docRequestRecordFragment);
                }
                else if(choice.equals("measurement")){

                }
            }
        });
    }
}