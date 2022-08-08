
package com.example.hospitalsystem_abdelrahmantarek.Models.Cases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaseCardData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
