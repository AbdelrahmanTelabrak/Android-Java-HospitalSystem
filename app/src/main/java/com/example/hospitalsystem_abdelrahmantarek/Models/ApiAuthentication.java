package com.example.hospitalsystem_abdelrahmantarek.Models;

import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.LoginResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.RegisterRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Authentication.RegisterResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.AcceptRejectResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CallsResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CreateCallRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Calls.CreateCallResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.AddNurseResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CasesResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.ShowCaseResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.CreateTaskRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.CreateTaskResponse;
import com.example.hospitalsystem_abdelrahmantarek.Models.Tasks.TasksResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiAuthentication {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @GET("calls?")
    Call<CallsResponse> getCalls(@Query("date") String date,
                                 @Header("Authorization") String token);

    @GET("doctors?")
    Call<DNAResponse> getDNA(@Query("type") String type,
                             @Header("Authorization") String token);

    @POST("calls")
    Call<CreateCallResponse> createCall(@Body CreateCallRequest callRequest,
                                        @Header("Authorization") String token);

    @FormUrlEncoded
    @PUT("calls-accept/{callId}")
    Call<AcceptRejectResponse> acceptRejectCall(@Path("callId") int callId,
                                                @Field("status") String status,
                                                @Header("Authorization") String token);

    @GET("case")
    Call<CasesResponse> getCases(@Header("Authorization") String token);

    @GET("case/{caseId}")
    Call<ShowCaseResponse> showCase(@Path("caseId") int caseId,
                                    @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("add-nurse")
    Call<AddNurseResponse> addNurse(@Field("call_id") int caseId,
                                    @Field("user_id") int nurseId,
                                    @Header("Authorization") String token);

    @GET("tasks?")
    Call<TasksResponse> getTasks(@Query("date") String date,
                                 @Header("Authorization") String token);

    @POST("tasks")
    Call<CreateTaskResponse> createTask(@Body CreateTaskRequest request,
                                        @Header("Authorization") String token);
}
