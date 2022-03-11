package com.example.hospitalsystem_abdelrahmantarek.HrEmployee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.hospitalsystem_abdelrahmantarek.EmployeeModel;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ActivityHrEmployeeBinding;

import java.util.ArrayList;

public class HrEmployeeActivity extends AppCompatActivity {

    ActivityHrEmployeeBinding binding;

    ArrayList<EmployeeModel> employeeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hr_employee);


    }
}