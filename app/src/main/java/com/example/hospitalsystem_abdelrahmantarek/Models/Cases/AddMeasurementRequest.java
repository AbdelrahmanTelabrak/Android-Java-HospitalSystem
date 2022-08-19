package com.example.hospitalsystem_abdelrahmantarek.Models.Cases;

import com.google.gson.annotations.SerializedName;

public class AddMeasurementRequest {

    @SerializedName("call_id")
    private int caseId;

    @SerializedName("blood_pressure")
    private String bloodP;

    @SerializedName("sugar_analysis")
    private String sugarA;

    @SerializedName("note")
    private String mNote;

    @SerializedName("status")
    private String status = "pending";

    public AddMeasurementRequest(int caseId, String bloodP, String sugarA, String mNote) {
        this.caseId = caseId;
        this.bloodP = bloodP;
        this.sugarA = sugarA;
        this.mNote = mNote;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getBloodP() {
        return bloodP;
    }

    public void setBloodP(String bloodP) {
        this.bloodP = bloodP;
    }

    public String getSugarA() {
        return sugarA;
    }

    public void setSugarA(String sugarA) {
        this.sugarA = sugarA;
    }

    public String getmNote() {
        return mNote;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
