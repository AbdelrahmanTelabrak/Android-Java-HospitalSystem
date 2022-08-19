package com.example.hospitalsystem_abdelrahmantarek.Models.Cases;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RequestAnlRequest {

    @SerializedName("call_id")
    private int caseId;

    @SerializedName("user_id")
    private int empId;

    @SerializedName("note")
    private String note;

    @SerializedName("types")
    private ArrayList<String> list;

    public RequestAnlRequest(int caseId, int empId, String note, ArrayList<String> list) {
        this.caseId = caseId;
        this.empId = empId;
        this.note = note;
        this.list = list;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<String > getList() {
        return list;
    }

    public void setList(ArrayList<String > list) {
        this.list = list;
    }
}
