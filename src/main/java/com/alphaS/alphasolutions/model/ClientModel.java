package com.alphaS.alphasolutions.model;

public class ClientModel {

    private String clientName;
    private int companyPhone;
    private String contactPerson;
    private int contactPhone;
    private String adress;
    private int zipcode;
    private String country;

    public ClientModel(String clientName, int companyPhone, String contactPerson, int contactPhone, String adress, int zipcode, String country) {
        this.clientName = clientName;
        this.companyPhone = companyPhone;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.adress = adress;
        this.zipcode = zipcode;
        this.country = country;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(int companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(int contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
                "clientName='" + clientName + '\'' +
                ", companyPhone=" + companyPhone +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactPhone=" + contactPhone +
                ", adress='" + adress + '\'' +
                ", zipcode=" + zipcode +
                ", country='" + country + '\'' +
                '}';
    }
}
