package com.example.hospitalsystem_abdelrahmantarek.Receptionist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.RecCallsAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.DateBottomSheetFragment;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentCallsBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallsFragment extends Fragment {

    FragmentCallsBinding binding;

    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calls, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date of birth")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

        RetrofitClient.getClient().getCalls("","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMjhkNGIxZjQwMTIxYWFlN2ZiZWMwZTdlYWU5M2E5N2Q0MWIyNDI3MDg5ZDZmNDFhZWU0YzAxMmMwZWFlN2E2MWYwYjEzMGQzMjU1YTI2YmMiLCJpYXQiOjE2NTk0NDgwODAuODQ5NTI4LCJuYmYiOjE2NTk0NDgwODAuODQ5NTMzLCJleHAiOjE2OTA5ODQwODAuODEwNzQxLCJzdWIiOiIyMDkiLCJzY29wZXMiOltdfQ.o26kZ4I6H9bwWSxirLNrjiGvRiHhC-k-hPqDExAJv1qubUtHwOjYQU_uLi_Xd99knnMRjvS4yXLGTBBsrjk1_jTAK1KAIn5Is1YjFT4NMOEhxOqswzMGesFeeDmZgNzoyN6nnV890T5nnsA1g8hQJEuLqh7P6eqkBAOzEhq7z6rwv9ycw5ZOZznhtlEKZxKuja2xCC1wdFzpTihDO-qb6S5xZ7S1WZD3ajqu1ig0PwS5PCWLCwivm6UNH3snuvFu1jAVCGH8qgfEaX358COrfuOT4pXfACXNZvG5DYefUs7Tqo2jStjmIAkO4DBWu2BnCjk2zNpbE_ZkqbmahJI8bsXiQS3uPvJ0NKdVxfRg5sk_3oow5rPqiq1Oe7mQhYCPLczhQlkcUwhJxPClRF8au7Z7j3wjsj98hYBZjE2NY0vXpBNjk9bvcQyopCz9TZ0d8AjU2PGJs3UI_wybItjg8jjqSiEkXY5vXqN39cnfZtHj39L2bTcrm5xaQ07YDIsZAqKw4jifCMhAKV0PdUmgfjXcQWnsHVOPbwVWlxvxkF7e9m8kEaifzwmTiQlmTGQ_JtJWWFcq03nCBHZ1gIJNK3humjhBsNBGYDtb-VjfTe4Rv-MeeoYMFll8dIH4TBNUv7JAWzoRARPkzxh1Sd3fUmYOpQNf9OXHhnQBEhqYOJE").
                enqueue(new Callback<CallsResponse>() {
                    @Override
                    public void onResponse(Call<CallsResponse> call, Response<CallsResponse> response) {
                        if(response.isSuccessful())
                        {
                            if(response.body().isSuccess())
                            {
                                RecCallsAdaptor callsAdaptor = new RecCallsAdaptor(response.body().getData());
                                binding.rvCallsListCalls.setAdapter(callsAdaptor);
                            }
                            else{
                                String errorMessage = response.body().getMessage();
                                Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            handleFailedResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<CallsResponse> call, Throwable t) {
                        Toast.makeText(view.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        binding.ibDateBtnCalls.setOnClickListener(new View.OnClickListener() {
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
                        binding.etiRecCallsDate.setText(formattedDate);

                        RetrofitClient.getClient().getCalls(formattedDate,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMjhkNGIxZjQwMTIxYWFlN2ZiZWMwZTdlYWU5M2E5N2Q0MWIyNDI3MDg5ZDZmNDFhZWU0YzAxMmMwZWFlN2E2MWYwYjEzMGQzMjU1YTI2YmMiLCJpYXQiOjE2NTk0NDgwODAuODQ5NTI4LCJuYmYiOjE2NTk0NDgwODAuODQ5NTMzLCJleHAiOjE2OTA5ODQwODAuODEwNzQxLCJzdWIiOiIyMDkiLCJzY29wZXMiOltdfQ.o26kZ4I6H9bwWSxirLNrjiGvRiHhC-k-hPqDExAJv1qubUtHwOjYQU_uLi_Xd99knnMRjvS4yXLGTBBsrjk1_jTAK1KAIn5Is1YjFT4NMOEhxOqswzMGesFeeDmZgNzoyN6nnV890T5nnsA1g8hQJEuLqh7P6eqkBAOzEhq7z6rwv9ycw5ZOZznhtlEKZxKuja2xCC1wdFzpTihDO-qb6S5xZ7S1WZD3ajqu1ig0PwS5PCWLCwivm6UNH3snuvFu1jAVCGH8qgfEaX358COrfuOT4pXfACXNZvG5DYefUs7Tqo2jStjmIAkO4DBWu2BnCjk2zNpbE_ZkqbmahJI8bsXiQS3uPvJ0NKdVxfRg5sk_3oow5rPqiq1Oe7mQhYCPLczhQlkcUwhJxPClRF8au7Z7j3wjsj98hYBZjE2NY0vXpBNjk9bvcQyopCz9TZ0d8AjU2PGJs3UI_wybItjg8jjqSiEkXY5vXqN39cnfZtHj39L2bTcrm5xaQ07YDIsZAqKw4jifCMhAKV0PdUmgfjXcQWnsHVOPbwVWlxvxkF7e9m8kEaifzwmTiQlmTGQ_JtJWWFcq03nCBHZ1gIJNK3humjhBsNBGYDtb-VjfTe4Rv-MeeoYMFll8dIH4TBNUv7JAWzoRARPkzxh1Sd3fUmYOpQNf9OXHhnQBEhqYOJE").
                                enqueue(new Callback<CallsResponse>() {
                                    @Override
                                    public void onResponse(Call<CallsResponse> call, Response<CallsResponse> response) {
                                        if(response.isSuccessful())
                                        {
                                            if(response.body().isSuccess())
                                            {
                                                RecCallsAdaptor callsAdaptor = new RecCallsAdaptor(response.body().getData());
                                                binding.rvCallsListCalls.setAdapter(callsAdaptor);
                                            }
                                        }
                                        else
                                        {
                                            handleFailedResponse(response);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CallsResponse> call, Throwable t) {
                                        Toast.makeText(view.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
            }
        });

        binding.ibAddCallReceptionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_callsFragment_to_createCallFragment);
            }
        });
    }

    private void handleFailedResponse(Response<CallsResponse> response) {
        try {
            String errorResponse = response.errorBody().string();

            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}