package com.example.hospitalsystem_abdelrahmantarek.Models;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//class for singleton pattern
public class RetrofitClient {

    private RetrofitClient(){}

    private static Retrofit retrofit = null;

    public static ApisFunctions getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.instant-ss.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApisFunctions.class);
    }
}
