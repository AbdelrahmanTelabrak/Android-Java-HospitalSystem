
package com.example.hospitalsystem_abdelrahmantarek.Models.Reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportCardData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("report_name")
    @Expose
    private String reportName;
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

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
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
