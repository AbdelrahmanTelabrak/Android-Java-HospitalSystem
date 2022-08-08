package com.example.hospitalsystem_abdelrahmantarek.Doctor;

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
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.DocCallsListAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocCallsListBinding;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DocCallsListFragment extends Fragment {

    FragmentDocCallsListBinding binding;
    NavController navController;
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




        RetrofitClient.getClient().getCalls("", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiODkxNDI5ZDNmMGM3YzNkNDAzNzU4ODA0ZWYzZjk2Y2M5MGRiZGQwZDc5N2E0NTE1MTFjMTI1YzMxMjQ4YjAzOGU3YWY5NzkzNmFlMDFhMmEiLCJpYXQiOjE2NTk1MzE2NzIuNTM2NDAyLCJuYmYiOjE2NTk1MzE2NzIuNTM2NDEsImV4cCI6MTY5MTA2NzY3Mi40NDEyODIsInN1YiI6IjIwNiIsInNjb3BlcyI6W119.q71BAp8wKeLc_uU4EevFMMlbyBSu-mN3DnBmhR8hsLdkfqq_Ae7JkypDIe_83rRE3iBq6DPLdoy3xS3eBhMVFk7Rz7WNCavfhN3NCsBDdZZtRpMTvWThMg3AdrbhszJ057pmZuumnVwBsSp9PjTwYlwNJqNEXnedNc-S2qq4f_eYxqlTctl_gLwxPnmPGQ2gtHlBDp_-QOGbf0rpz0smqcZlJ9VODZfXgY_ZeWaC-w3xf81zUbpzn9AJRjKM4gi9ojxtJp7l0cxpaTLqpLQDejbwoPCMEHqvxieUJVl62TCjxGjm7sWvv5-f77e6tkV6w-4XbJDP0oKVuCu2OwekoGmRPx1t583GbjWlHnTW8gXwcgakxdgdPxyIeAxsao5Wqf5NHodMjk-jVkNqqDXUhe4Lt3Cnc20_3WoML7gMZoe2gCUzpB-SqYvbiUQ6R_jWhc9sUYdGVXWlnYyTNntGTMWj9B8ek1NQqW_612F9A63uvlABNj1LNkbdfw7n1QieNr66Aw42nWXibEw2K5BbxtunYuEfS4t8nuo7YqeTF1zHWXt9IarDybuNRGglpymXLBufNhtPSTan90Qmkuqp6cGzt8dKE-RV372BlRvC0wu7UbloNt6OKMEjjTc1Otkis55rPV0hpTV6Lt57I8NQEKVjBicm5FekyZg1Z4O5CPY")
                .enqueue(new Callback<CallsResponse>() {
                    @Override
                    public void onResponse(Call<CallsResponse> call, Response<CallsResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().isSuccess()){
                                dclAdaptor = new DocCallsListAdaptor(response.body().getData());
                                binding.rvDclList.setAdapter(dclAdaptor);
                            }
                            else {
                                String errorMessage = response.body().getMessage();
                                Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            handleFailedResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<CallsResponse> call, Throwable t) {
                        Toast.makeText(view.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        binding.ibDclBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_docCallsListFragment_to_doctorMenuFragment);
            }
        });
    }

    private void handleFailedResponse(Response<CallsResponse> response) {
        try {
            String errorResponse = response.errorBody().string();
            System.out.println("ERROR RESPONSE ****************************\n"+errorResponse);

            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}