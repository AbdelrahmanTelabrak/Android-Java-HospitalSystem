package com.example.hospitalsystem_abdelrahmantarek.HrEmployee;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.RegisterRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.RegisterResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Authentication.RegisterViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentHrAddUserBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HrAddUserFragment extends Fragment {

    private static final String TAG = "HrAddUserFragment";
    FragmentHrAddUserBinding binding;
    NavController navController;
    RegisterViewModel registerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hr_add_user, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        //navController.popBackStack(R.id.employeeListFragment, true);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        String[] listGender = {"male", "female"};
        String[] listSpecialist = {"doctor", "receptionist", "nurse", "analysis", "hr", "manager"};
        String[] listStatus = {"single", "married"};

        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_list, listGender);
        ArrayAdapter<String> adapterSpecialist = new ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_list, listSpecialist);
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_list, listStatus);

        binding.etiHrNUGender.setAdapter(adapterGender);
        binding.etiHrNUSpecialist.setAdapter(adapterSpecialist);
        binding.etiHrNUStatus.setAdapter(adapterStatus);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date of birth")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

        binding.etiHrNUGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etiHrNUGender.showDropDown();
            }
        });

        binding.etiHrNUSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etiHrNUSpecialist.showDropDown();
            }
        });

        binding.etiHrNUStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etiHrNUStatus.showDropDown();
            }
        });

        binding.btnHrNUCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegisterRequest registerRequest = new RegisterRequest(binding.etiHrNUEmail.getText().toString(),
                        binding.etiHrNUPassword.getText().toString(), binding.etiHrNUFirstName.getText().toString(),
                        binding.etiHrNULastName.getText().toString(),binding.etiHrNUGender.getText().toString(),
                        binding.etiHrNUSpecialist.getText().toString(), binding.etiHrNUBirthday.getText().toString(),
                        binding.etiHrNUStatus.getText().toString(), binding.etiHrNUAddress.getText().toString(),
                        binding.etiHrNUMobile.getText().toString(), binding.etiHrNUSpecialist.getText().toString());

                if(handleTextFields()){

                }
            }
        });

        binding .etiHrNUBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(requireActivity().getSupportFragmentManager(), "datePicker");

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis(selection);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate  = format.format(calendar.getTime());
                        binding.etiHrNUBirthday.setText(formattedDate);
                    }
                });
            }
        });

        binding.ibBackBtnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_hrAddUserFragment_to_employeeListFragment);
            }
        });

        registerObserver();
    }

    private void registerObserver(){
        registerViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                navController.navigate(R.id.action_hrAddUserFragment_to_employeeListFragment);
            }
        });

        registerViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean handleTextFields() {
        boolean bool = false;

        if(binding.etiHrNUFirstName.getText().toString().isEmpty())
            binding.etlFirstNameNUHr.setError("First Name is required");
        if(binding.etiHrNULastName.getText().toString().isEmpty())
            binding.etlLastNameNUHr.setError("Last Name is required");
        if(binding.etiHrNUGender.getText().toString().isEmpty())
            binding.etlGenderNUHr.setError("Gender is required");
        if(binding.etiHrNUSpecialist.getText().toString().isEmpty())
            binding.etlSpecialistNUHr.setError("Specialist is required");
        if(binding.etiHrNUBirthday.getText().toString().isEmpty())
            binding.etlBirthdayNUHr.setError("TaskDetails of birth is required");
        if(binding.etiHrNUStatus.getText().toString().isEmpty())
            binding.etlStatusNUHr.setError("Status is required");
        if(binding.etiHrNUMobile.getText().toString().isEmpty())
            binding.etlPhoneNumberNUHr.setError("Phone number is required");
        if(binding.etiHrNUEmail.getText().toString().isEmpty())
            binding.etlEmailNUHr.setError("Email is required");
        if(binding.etiHrNUAddress.getText().toString().isEmpty())
            binding.etlAddressNUHr.setError("Address is required");
        if(binding.etiHrNUPassword.getText().toString().isEmpty())
            binding.etlPasswordNUHr.setError("Password is required");

        if(!binding.etiHrNUFirstName.getText().toString().isEmpty() && !binding.etiHrNULastName.getText().toString().isEmpty() &&
                !binding.etiHrNUGender.getText().toString().isEmpty() && !binding.etiHrNUSpecialist.getText().toString().isEmpty() &&
                !binding.etiHrNUBirthday.getText().toString().isEmpty() && !binding.etiHrNUStatus.getText().toString().isEmpty() &&
                !binding.etiHrNUMobile.getText().toString().isEmpty() && !binding.etiHrNUEmail.getText().toString().isEmpty() &&
                !binding.etiHrNUAddress.getText().toString().isEmpty() && !binding.etiHrNUPassword.getText().toString().isEmpty())
            bool = true;

        return bool;
    }
    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }
}