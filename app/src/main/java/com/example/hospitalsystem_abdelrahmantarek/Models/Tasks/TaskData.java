
package com.example.hospitalsystem_abdelrahmantarek.Models.Tasks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("task_name")
    @Expose
    private String taskName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
