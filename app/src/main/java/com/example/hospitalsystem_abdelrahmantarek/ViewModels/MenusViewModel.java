package com.example.hospitalsystem_abdelrahmantarek.ViewModels;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hospitalsystem_abdelrahmantarek.Models.EmployeeModel;
import com.google.gson.Gson;

public class MenusViewModel {
    SharedPreferences preferences;
    EmployeeModel employeeModel;

    public MenusViewModel(Context context) {
        this.preferences = context.getSharedPreferences("empData", Context.MODE_PRIVATE);
        this.employeeModel = new Gson().fromJson(this.preferences.getString("employee", "null"), EmployeeModel.class);
    }

    public void resetPreference(){
        this.preferences.edit().putString("employee", "null").apply();
    }

    public EmployeeModel getEmployeeModel(){
        return this.employeeModel;
    }
}
