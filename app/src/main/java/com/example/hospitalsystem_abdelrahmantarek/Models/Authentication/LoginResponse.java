
package com.example.hospitalsystem_abdelrahmantarek.Models.Authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private LoginData loginData;

    public boolean isSuccess()
    {
        return status == 1;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginData getData() {
        return loginData;
    }

    public void setData(LoginData loginData) {
        this.loginData = loginData;
    }

}
