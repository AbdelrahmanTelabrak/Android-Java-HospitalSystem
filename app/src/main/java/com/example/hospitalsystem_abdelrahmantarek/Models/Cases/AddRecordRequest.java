package com.example.hospitalsystem_abdelrahmantarek.Models.Cases;

import com.google.gson.annotations.SerializedName;

import java.io.File;

import okhttp3.MultipartBody;

public class AddRecordRequest {

    @SerializedName("call_id")
    private int caseId;

    @SerializedName("note")
    private String note;

    @SerializedName("status")
    private String status;

    public AddRecordRequest(int caseId, String note, String status) {
        this.caseId = caseId;
        this.note = note;
        this.status = status;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
