
package com.example.hospitalsystem_abdelrahmantarek.Models.Employees;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DNAResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<DNAData> data = null;

    public boolean isSuccess(){
        return status==1;
    }

    public Integer getStatus() {
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

    public ArrayList<DNAData> getData() {
        return data;
    }

    public void setData(ArrayList<DNAData> data) {
        this.data = data;
    }

}
