package com.example.hospitalsystem_abdelrahmantarek.Attendance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.MenusViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentAttendanceMenuBinding;

public class AttendanceMenuFragment extends Fragment {
    FragmentAttendanceMenuBinding binding;
    NavController navController;
    MenusViewModel menusViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendance_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        navController.popBackStack(R.id.attendanceRegisteredFragment, true);

        menusViewModel = new MenusViewModel(requireContext());
        binding.tvAttendanceEmpName.setText(menusViewModel.getEmployeeModel().getFullName());
        binding.tvAttendanceSpecialist.setText("Specialist , "+ menusViewModel.getEmployeeModel().getType());

        binding.btnAttendanceAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttendanceMenuFragmentDirections.ActionAttendanceMenuFragmentToTouchIdFragment action =
                        AttendanceMenuFragmentDirections.actionAttendanceMenuFragmentToTouchIdFragment("attendance");
                navController.navigate(action);
            }
        });

        binding.btnAttendanceLeaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttendanceMenuFragmentDirections.ActionAttendanceMenuFragmentToTouchIdFragment action =
                        AttendanceMenuFragmentDirections.actionAttendanceMenuFragmentToTouchIdFragment("leaving");
                navController.navigate(action);
            }
        });
    }
}