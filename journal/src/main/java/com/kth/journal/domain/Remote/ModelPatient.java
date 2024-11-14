package com.kth.journal.domain.Remote;

import java.util.Date;
import org.hl7.fhir.r4.model.Patient;

public class ModelPatient {

    private String id;

    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private String securityNumber;
    private String familyName;
    private String givenName;

    private String telecom;

    private String gender;

    private Date birthDate;

    private String maritalStatus;

    private String language;

    public ModelPatient(Patient patient) {
        this.id = patient.getIdElement().getIdPart();
        this.address = patient.getAddressFirstRep().getLine().get(0).getValue();
        this.city = patient.getAddressFirstRep().getCity();
        this.state = patient.getAddressFirstRep().getState();
        this.postalCode = patient.getAddressFirstRep().getPostalCode();
        this.country = patient.getAddressFirstRep().getCountry();
        this.securityNumber = patient.getIdentifierFirstRep().getValue();
        this.familyName = patient.getNameFirstRep().getFamily();
        this.givenName = patient.getNameFirstRep().getGivenAsSingleString();
        this.telecom = patient.getTelecomFirstRep().getValue();
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getTelecom() {
        return telecom;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getLanguage() {
        return language;
    }
}