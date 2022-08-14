
package com.example.hospitalsystem_abdelrahmantarek.Models.Reports;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportDetailsData {

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
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("user")
    @Expose
    private ReportEmpData user;
    @SerializedName("manger")
    @Expose
    private ReportMangerData manger;
    @SerializedName("answer")
    @Expose
    private String answer;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ReportEmpData getUser() {
        return user;
    }

    public void setUser(ReportEmpData user) {
        this.user = user;
    }

    public ReportMangerData getManger() {
        return manger;
    }

    public void setManger(ReportMangerData manger) {
        this.manger = manger;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
