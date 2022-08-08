package com.example.hospitalsystem_abdelrahmantarek.Models.Calls;

import com.google.gson.annotations.SerializedName;

public class CreateCallRequest {

    @SerializedName("patient_name")
    String patientName;

    @SerializedName("doctor_id")
    int docId;

    @SerializedName("age")
    String  age;

    @SerializedName("phone")
    String phone;

    @SerializedName("description")
    String description;

    public CreateCallRequest(String patientName, int docId, String age, String phone, String description) {
        this.patientName = patientName;
        this.docId = docId;
        this.age = age;
        this.phone = phone;
        this.description = description;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
