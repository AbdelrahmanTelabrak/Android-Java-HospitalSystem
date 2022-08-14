package com.example.hospitalsystem_abdelrahmantarek.Models.Reports;

import com.google.gson.annotations.SerializedName;

public class ManagerCreateReportRequest {
    @SerializedName("report_name")
    private String reportName;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int empId;

    public ManagerCreateReportRequest(String reportName, String description, int empId) {
        this.reportName = reportName;
        this.description = description;
        this.empId = empId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }
}
