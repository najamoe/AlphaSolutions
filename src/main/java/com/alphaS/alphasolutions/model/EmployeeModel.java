package com.alphaS.alphasolutions.model;

public class EmployeeModel {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private int phoneNo;
    private String country;
    private String jobTitle;

    public EmployeeModel(String employeeId, String firstName, String lastName, String email, String username, String password, int phoneNo, String country, String jobTitle) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNo = phoneNo;
        this.country = country;
        this.jobTitle = jobTitle;
    }
    public EmployeeModel() {

    }

    public EmployeeModel(String username, String password) {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "employeeId='" + employeeId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNo=" + phoneNo +
                ", country='" + country + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}

