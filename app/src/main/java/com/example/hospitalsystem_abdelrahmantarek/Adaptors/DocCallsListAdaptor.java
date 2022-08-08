package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.AcceptRejectResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.ErrorResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.RetrofitClient;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemDoctorCallsBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocCallsListAdaptor extends RecyclerView.Adapter<DocCallsListAdaptor.Holder> {
    ArrayList<CallData> callsList;

    public DocCallsListAdaptor(ArrayList<CallData> callsList) {
        this.callsList = callsList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorCallsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_doctor_calls, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CallData callData = callsList.get(position);
        holder.binding.itemDocCallTvName.setText(callData.getPatientName());
        holder.binding.itemDocCallTvBirthdate.setText(callData.getCreatedAt());
        holder.binding.btnItemDCAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getClient().acceptRejectCall(callData.getId(), "accept", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiODkxNDI5ZDNmMGM3YzNkNDAzNzU4ODA0ZWYzZjk2Y2M5MGRiZGQwZDc5N2E0NTE1MTFjMTI1YzMxMjQ4YjAzOGU3YWY5NzkzNmFlMDFhMmEiLCJpYXQiOjE2NTk1MzE2NzIuNTM2NDAyLCJuYmYiOjE2NTk1MzE2NzIuNTM2NDEsImV4cCI6MTY5MTA2NzY3Mi40NDEyODIsInN1YiI6IjIwNiIsInNjb3BlcyI6W119.q71BAp8wKeLc_uU4EevFMMlbyBSu-mN3DnBmhR8hsLdkfqq_Ae7JkypDIe_83rRE3iBq6DPLdoy3xS3eBhMVFk7Rz7WNCavfhN3NCsBDdZZtRpMTvWThMg3AdrbhszJ057pmZuumnVwBsSp9PjTwYlwNJqNEXnedNc-S2qq4f_eYxqlTctl_gLwxPnmPGQ2gtHlBDp_-QOGbf0rpz0smqcZlJ9VODZfXgY_ZeWaC-w3xf81zUbpzn9AJRjKM4gi9ojxtJp7l0cxpaTLqpLQDejbwoPCMEHqvxieUJVl62TCjxGjm7sWvv5-f77e6tkV6w-4XbJDP0oKVuCu2OwekoGmRPx1t583GbjWlHnTW8gXwcgakxdgdPxyIeAxsao5Wqf5NHodMjk-jVkNqqDXUhe4Lt3Cnc20_3WoML7gMZoe2gCUzpB-SqYvbiUQ6R_jWhc9sUYdGVXWlnYyTNntGTMWj9B8ek1NQqW_612F9A63uvlABNj1LNkbdfw7n1QieNr66Aw42nWXibEw2K5BbxtunYuEfS4t8nuo7YqeTF1zHWXt9IarDybuNRGglpymXLBufNhtPSTan90Qmkuqp6cGzt8dKE-RV372BlRvC0wu7UbloNt6OKMEjjTc1Otkis55rPV0hpTV6Lt57I8NQEKVjBicm5FekyZg1Z4O5CPY")
                        .enqueue(new Callback<AcceptRejectResponse>() {
                            @Override
                            public void onResponse(Call<AcceptRejectResponse> call, Response<AcceptRejectResponse> response) {
                                if (response.isSuccessful()){
                                    if (response.body().isSuccess()){
                                        callsList.remove(holder.getAdapterPosition());
                                        notifyItemRemoved(holder.getAdapterPosition());
                                    }
                                    else {
                                        String errorMessage = response.body().getMessage();
                                        Toast.makeText(holder.binding.getRoot().getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    handleFailedResponse(holder, response);
                                }
                            }

                            @Override
                            public void onFailure(Call<AcceptRejectResponse> call, Throwable t) {
                                Toast.makeText(holder.binding.getRoot().getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        holder.binding.btnItemDCBusy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getClient().acceptRejectCall(callData.getId(), "reject", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiODkxNDI5ZDNmMGM3YzNkNDAzNzU4ODA0ZWYzZjk2Y2M5MGRiZGQwZDc5N2E0NTE1MTFjMTI1YzMxMjQ4YjAzOGU3YWY5NzkzNmFlMDFhMmEiLCJpYXQiOjE2NTk1MzE2NzIuNTM2NDAyLCJuYmYiOjE2NTk1MzE2NzIuNTM2NDEsImV4cCI6MTY5MTA2NzY3Mi40NDEyODIsInN1YiI6IjIwNiIsInNjb3BlcyI6W119.q71BAp8wKeLc_uU4EevFMMlbyBSu-mN3DnBmhR8hsLdkfqq_Ae7JkypDIe_83rRE3iBq6DPLdoy3xS3eBhMVFk7Rz7WNCavfhN3NCsBDdZZtRpMTvWThMg3AdrbhszJ057pmZuumnVwBsSp9PjTwYlwNJqNEXnedNc-S2qq4f_eYxqlTctl_gLwxPnmPGQ2gtHlBDp_-QOGbf0rpz0smqcZlJ9VODZfXgY_ZeWaC-w3xf81zUbpzn9AJRjKM4gi9ojxtJp7l0cxpaTLqpLQDejbwoPCMEHqvxieUJVl62TCjxGjm7sWvv5-f77e6tkV6w-4XbJDP0oKVuCu2OwekoGmRPx1t583GbjWlHnTW8gXwcgakxdgdPxyIeAxsao5Wqf5NHodMjk-jVkNqqDXUhe4Lt3Cnc20_3WoML7gMZoe2gCUzpB-SqYvbiUQ6R_jWhc9sUYdGVXWlnYyTNntGTMWj9B8ek1NQqW_612F9A63uvlABNj1LNkbdfw7n1QieNr66Aw42nWXibEw2K5BbxtunYuEfS4t8nuo7YqeTF1zHWXt9IarDybuNRGglpymXLBufNhtPSTan90Qmkuqp6cGzt8dKE-RV372BlRvC0wu7UbloNt6OKMEjjTc1Otkis55rPV0hpTV6Lt57I8NQEKVjBicm5FekyZg1Z4O5CPY")
                        .enqueue(new Callback<AcceptRejectResponse>() {
                            @Override
                            public void onResponse(Call<AcceptRejectResponse> call, Response<AcceptRejectResponse> response) {
                                if (response.isSuccessful()){
                                    if (response.body().isSuccess()){
                                        callsList.remove(holder.getAdapterPosition());
                                        notifyItemRemoved(holder.getAdapterPosition());
                                    }
                                    else {
                                        String errorMessage = response.body().getMessage();
                                        Toast.makeText(holder.binding.getRoot().getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    handleFailedResponse(holder, response);
                                }
                            }

                            @Override
                            public void onFailure(Call<AcceptRejectResponse> call, Throwable t) {
                                Toast.makeText(holder.binding.getRoot().getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return callsList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ItemDoctorCallsBinding binding;
        public Holder(@NonNull ItemDoctorCallsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void handleFailedResponse(@NonNull Holder holder, Response<AcceptRejectResponse> response) {
        try {
            String errorResponse = response.errorBody().string();

            Gson gson = new Gson();
            ErrorResponse errorResponseObject = gson.fromJson(errorResponse, ErrorResponse.class);
            String errorMessage = errorResponseObject.getMessage();
            Toast.makeText(holder.binding.getRoot().getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
