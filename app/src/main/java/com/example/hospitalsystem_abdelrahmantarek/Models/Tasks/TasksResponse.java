
package com.example.hospitalsystem_abdelrahmantarek.Models.Tasks;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TasksResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<TaskData> data = null;

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

    public ArrayList<TaskData> getData() {
        return data;
    }

    public void setData(ArrayList<TaskData> data) {
        this.data = data;
    }

}
