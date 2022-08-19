package com.example.hospitalsystem_abdelrahmantarek.Models.Cases;

public class NurseReply {

    private String nurseFullName;
    private String measurementNote;
    private String bloodPressure;
    private String sugarAnalysis;
    private String temperature;
    private String fluidBalance;
    private String respiratoryRate;
    private String heartRate;

    public NurseReply(String nurseFullName, String measurementNote, String bloodPressure, String sugarAnalysis, String temperature, String fluidBalance, String respiratoryRate, String heartRate) {
        this.nurseFullName = nurseFullName;
        this.measurementNote = measurementNote;
        this.bloodPressure = bloodPressure;
        this.sugarAnalysis = sugarAnalysis;
        this.temperature = temperature;
        this.fluidBalance = fluidBalance;
        this.respiratoryRate = respiratoryRate;
        this.heartRate = heartRate;
    }

    public String getNurseFullName() {
        return nurseFullName;
    }

    public void setNurseFullName(String nurseFullName) {
        this.nurseFullName = nurseFullName;
    }

    public String getMeasurementNote() {
        return measurementNote;
    }

    public void setMeasurementNote(String measurementNote) {
        this.measurementNote = measurementNote;
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

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
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


    @Override
    public String toString() {
        return "NurseReply{" +
                "nurseFullName='" + nurseFullName + '\'' +
                ", measurementNote='" + measurementNote + '\'' +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", sugarAnalysis='" + sugarAnalysis + '\'' +
                ", temperature='" + temperature + '\'' +
                ", fluidBalance='" + fluidBalance + '\'' +
                ", respiratoryRate='" + respiratoryRate + '\'' +
                ", heartRate='" + heartRate + '\'' +
                '}';
    }
}
