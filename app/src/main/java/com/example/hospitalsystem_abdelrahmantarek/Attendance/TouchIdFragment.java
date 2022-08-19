package com.example.hospitalsystem_abdelrahmantarek.Attendance;

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

import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Employees.AttendanceViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentTouchIdBinding;

public class TouchIdFragment extends Fragment {
    FragmentTouchIdBinding binding;
    NavController navController;
    AttendanceViewModel attendanceViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_touch_id, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        String status = TouchIdFragmentArgs.fromBundle(getArguments()).getStatus();

        attendanceViewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);
        attendanceObserver();

        binding.ivFingerprint.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                attendanceViewModel.attendance(requireContext(), status);
                return false;
            }
        });
    }

    private void attendanceObserver(){
        attendanceViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                navController.navigate(R.id.action_touchIdFragment_to_attendanceRegisteredFragment);
            }
        });

        attendanceViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}