package com.example.hospitalsystem_abdelrahmantarek.Models.Tasks;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreateTaskRequest {

    @SerializedName("user_id")
    private int empId;

    @SerializedName("task_name")
    private String taskName;

    @SerializedName("description")
    private String description;

    @SerializedName("todos")
    private ArrayList<String> todos;

    public CreateTaskRequest(int empId, String taskName, String description, ArrayList<String> todos) {
        this.empId = empId;
        this.taskName = taskName;
        this.description = description;
        this.todos = todos;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<String> todos) {
        this.todos = todos;
    }
}