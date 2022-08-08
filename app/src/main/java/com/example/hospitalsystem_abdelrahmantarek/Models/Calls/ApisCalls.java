package com.example.hospitalsystem_abdelrahmantarek.Models.Calls;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApisCalls {

    @GET("calls?")
    Call<CallsResponse> getCalls(@Query("date") String date,
                                 @Header("Authorization") String token);
}
