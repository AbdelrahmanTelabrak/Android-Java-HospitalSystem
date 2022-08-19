package com.example.hospitalsystem_abdelrahmantarek.Models.Cases;

public class AnalysisReply {

    private String empName;
    private String imageUrl;

    public AnalysisReply(String empName, String imageUrl) {
        this.empName = empName;
        this.imageUrl = imageUrl;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
