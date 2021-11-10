package com.kimadrian.assignmentone.models;

public class UserDetails {
    String firstName;
    String lastName;
    String gender;
    String idNumber;
    String address;

    public UserDetails() {
    }

    public UserDetails(String firstName, String lastName, String gender, String idNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.idNumber = idNumber;
        this.address = address;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
