package com.example.hospitalsystem_abdelrahmantarek.Receptionist;

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

import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CreateCallRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CreateCallResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.DocNameId;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentCreateCallBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateCallFragment extends Fragment {

    FragmentCreateCallBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_call, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        DocNameId docNameId = CreateCallFragmentArgs.fromBundle(getArguments()).getDocData();

        if(CreateCallFragmentArgs.fromBundle(getArguments()).getDocData() != null){
            assert docNameId != null;
            binding.etiCCSelectDoctor.setText(docNameId.getName());
        }

        binding.etiCCSelectDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_createCallFragment_to_selectDoctorFragment);
            }
        });

        binding.btnCreateCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCallRequest callRequest = new CreateCallRequest(binding.etiCCPatientName.getText().toString(), Integer.valueOf(CreateCallFragmentArgs.fromBundle(getArguments()).getDocData().getId()),
                        binding.etiCCAge.getText().toString(), binding.etiCCMobile.getText().toString(),
                        binding.etiCCDescription.getText().toString());

                RetrofitClient.getClient().createCall(callRequest, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMjhkNGIxZjQwMTIxYWFlN2ZiZWMwZTdlYWU5M2E5N2Q0MWIyNDI3MDg5ZDZmNDFhZWU0YzAxMmMwZWFlN2E2MWYwYjEzMGQzMjU1YTI2YmMiLCJpYXQiOjE2NTk0NDgwODAuODQ5NTI4LCJuYmYiOjE2NTk0NDgwODAuODQ5NTMzLCJleHAiOjE2OTA5ODQwODAuODEwNzQxLCJzdWIiOiIyMDkiLCJzY29wZXMiOltdfQ.o26kZ4I6H9bwWSxirLNrjiGvRiHhC-k-hPqDExAJv1qubUtHwOjYQU_uLi_Xd99knnMRjvS4yXLGTBBsrjk1_jTAK1KAIn5Is1YjFT4NMOEhxOqswzMGesFeeDmZgNzoyN6nnV890T5nnsA1g8hQJEuLqh7P6eqkBAOzEhq7z6rwv9ycw5ZOZznhtlEKZxKuja2xCC1wdFzpTihDO-qb6S5xZ7S1WZD3ajqu1ig0PwS5PCWLCwivm6UNH3snuvFu1jAVCGH8qgfEaX358COrfuOT4pXfACXNZvG5DYefUs7Tqo2jStjmIAkO4DBWu2BnCjk2zNpbE_ZkqbmahJI8bsXiQS3uPvJ0NKdVxfRg5sk_3oow5rPqiq1Oe7mQhYCPLczhQlkcUwhJxPClRF8au7Z7j3wjsj98hYBZjE2NY0vXpBNjk9bvcQyopCz9TZ0d8AjU2PGJs3UI_wybItjg8jjqSiEkXY5vXqN39cnfZtHj39L2bTcrm5xaQ07YDIsZAqKw4jifCMhAKV0PdUmgfjXcQWnsHVOPbwVWlxvxkF7e9m8kEaifzwmTiQlmTGQ_JtJWWFcq03nCBHZ1gIJNK3humjhBsNBGYDtb-VjfTe4Rv-MeeoYMFll8dIH4TBNUv7JAWzoRARPkzxh1Sd3fUmYOpQNf9OXHhnQBEhqYOJE")
                        .enqueue(new Callback<CreateCallResponse>() {
                            @Override
                            public void onResponse(Call<CreateCallResponse> call, Response<CreateCallResponse> response) {
                                if (response.isSuccessful()){
                                    if (response.body().isSuccess()){
                                        navController.navigate(R.id.action_createCallFragment_to_PCompletedFragment);
                                    }
                                    else {
                                        String errorMessage = response.body().getMessage();
                                        Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    handleFailedResponse(response);
                                }
                            }

                            @Override
                            public void onFailure(Call<CreateCallResponse> call, Throwable t) {
                                Toast.makeText(view.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    private void handleFailedResponse(Response<CreateCallResponse> response) {
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