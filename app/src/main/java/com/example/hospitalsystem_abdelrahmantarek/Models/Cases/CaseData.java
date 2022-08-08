
package com.example.hospitalsystem_abdelrahmantarek.Models.Cases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaseData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("doctor_id")
    @Expose
    private String doctorId;
    @SerializedName("doc_id")
    @Expose
    private Integer docId;
    @SerializedName("nurse_id")
    @Expose
    private String nurseId;
    @SerializedName("analysis_id")
    @Expose
    private String analysisId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("case_status")
    @Expose
    private String caseStatus;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("blood_pressure")
    @Expose
    private String bloodPressure;
    @SerializedName("sugar_analysis")
    @Expose
    private String sugarAnalysis;
    @SerializedName("tempreture")
    @Expose
    private String tempreture;
    @SerializedName("fluid_balance")
    @Expose
    private String fluidBalance;
    @SerializedName("respiratory_rate")
    @Expose
    private String respiratoryRate;
    @SerializedName("heart_rate")
    @Expose
    private String heartRate;
    @SerializedName("measurement_note")
    @Expose
    private String measurementNote;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("medical_record_note")
    @Expose
    private String medicalRecordNote;

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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(String analysisId) {
        this.analysisId = analysisId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
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

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getSugarAnalysis() {
        return sugarAnalysis;
    }

    public void setSugarAnalysis(String sugarAnalysis) {
        this.sugarAnalysis = sugarAnalysis;
    }

    public String getTempreture() {
        return tempreture;
    }

    public void setTempreture(String tempreture) {
        this.tempreture = tempreture;
    }

    public String getFluidBalance() {
        return fluidBalance;
    }

    public void setFluidBalance(String fluidBalance) {
        this.fluidBalance = fluidBalance;
    }

    public String getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(String respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getMeasurementNote() {
        return measurementNote;
    }

    public void setMeasurementNote(String measurementNote) {
        this.measurementNote = measurementNote;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMedicalRecordNote() {
        return medicalRecordNote;
    }

    public void setMedicalRecordNote(String medicalRecordNote) {
        this.medicalRecordNote = medicalRecordNote;
    }

}
