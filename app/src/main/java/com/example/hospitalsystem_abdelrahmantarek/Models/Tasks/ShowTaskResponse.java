
package com.example.hospitalsystem_abdelrahmantarek.Models.Tasks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowTaskResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private TaskDetails data;

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

    public TaskDetails getData() {
        return data;
    }

    public void setData(TaskDetails data) {
        this.data = data;
    }

}
