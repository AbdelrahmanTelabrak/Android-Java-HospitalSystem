package com.example.hospitalsystem_abdelrahmantarek.Models.Reports;

import com.google.gson.annotations.SerializedName;

public class CreateReportRequest {

    @SerializedName("report_name")
    private String reportName;

    @SerializedName("description")
    private String description;

    public CreateReportRequest(String reportName, String description) {
        this.reportName = reportName;
        this.description = description;
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
}
