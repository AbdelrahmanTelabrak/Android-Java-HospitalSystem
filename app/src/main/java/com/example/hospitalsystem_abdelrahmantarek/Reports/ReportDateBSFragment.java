package com.example.hospitalsystem_abdelrahmantarek.Reports;

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
import android.widget.CalendarView;

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentReportDateBSBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ReportDateBSFragment extends BottomSheetDialogFragment {
    FragmentReportDateBSBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_date_b_s, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        binding.cvReports.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i+"-"+(i1+1)+"-"+i2;
                ReportDateBSFragmentDirections.ActionReportDateBSFragmentToReportsListsFragment action =
                        ReportDateBSFragmentDirections.actionReportDateBSFragmentToReportsListsFragment().setDate(date);
                navController.navigate(action);
            }
        });
    }
}