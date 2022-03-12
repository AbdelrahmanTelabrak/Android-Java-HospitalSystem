package com.example.hospitalsystem_abdelrahmantarek.Models;

public class EmployeeModel {

    private String name;
    private String job;
    private String status = "Available";
    private String imageUrl;


    public EmployeeModel(String eName, String eJob, String eStatus, String eImageUrl) {
        this.name = eName;
        this.job = eJob;
        this.status = eStatus;
        this.imageUrl = eImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", status='" + status + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
