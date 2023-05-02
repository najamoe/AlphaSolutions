package com.alphaS.alphasolutions.model;

public class ClientModel {

    private int clientId;
    private String clientName;
    private int companyPoNo;
    private String contactPerson;
    private int contactPoNo;
    private String address;
    private int zipcode;
    private String country;

    public ClientModel(int clientId, String clientName, int companyPoNo, String contactPerson, int contactPoNo, String address, int zipcode, String country) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.companyPoNo = companyPoNo;
        this.contactPerson = contactPerson;
        this.contactPoNo = contactPoNo;
        this.address = address;
        this.zipcode = zipcode;
        this.country = country;
    }

    public ClientModel() {

    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getCompanyPoNo() {
        return companyPoNo;
    }

    public void setCompanyPoNo(int companyPoNo) {
        this.companyPoNo = companyPoNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getContactPoNo() {
        return contactPoNo;
    }

    public void setContactPoNo(int contactPoNo) {
        this.contactPoNo = contactPoNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "ClientModel{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", companyPoNo=" + companyPoNo +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactPoNo=" + contactPoNo +
                ", address='" + address + '\'' +
                ", zipcode=" + zipcode +
                ", country='" + country + '\'' +
                '}';
    }
}