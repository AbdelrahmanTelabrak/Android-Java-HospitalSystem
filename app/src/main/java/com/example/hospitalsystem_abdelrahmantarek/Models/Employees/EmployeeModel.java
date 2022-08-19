package com.example.hospitalsystem_abdelrahmantarek.Models.Employees;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeModel {

    private String firstName;
    private String lastName;
    private String fullName;
    private String mobile;
    private String email;
    private String gender;
    private String status;
    private String type;
    private String birthday;
    private String address;
    private String accessToken;

    public EmployeeModel(String firstName, String lastName, String mobile, String email, String gender, String status, String type, String birthday, String address, String accessToken) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.mobile = mobile;
        this.email = email;
        this.gender = gender;
        this.status = status;
        this.type = type;
        this.birthday = birthday;
        this.address = address;
        this.accessToken ="Bearer "+ accessToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
