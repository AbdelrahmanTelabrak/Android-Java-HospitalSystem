package com.example.hospitalsystem_abdelrahmantarek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.hospitalsystem_abdelrahmantarek.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}