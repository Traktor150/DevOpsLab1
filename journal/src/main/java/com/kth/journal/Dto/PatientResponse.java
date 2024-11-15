package com.kth.journal.Dto;

import java.util.List;
import java.util.stream.Collectors;

import com.kth.journal.domain.Remote.ModelPatient;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientResponse {

    private final String id;

    private final String address;

    private final String city;

    private final String state;

    private final String postalCode;

    private final String country;

    private final String securityNumber;

    private final String familyName;

    private final String givenName;

    private final String telecom;

    private final String gender;

    private final String birthDate;

    private final String maritalStatus;

    private final String language;

    // constructor
    public PatientResponse(ModelPatient patient) {
        this.id = patient.getId();
        this.address = patient.getAddress();
        this.city = patient.getCity();
        this.state = patient.getState();
        this.postalCode = patient.getPostalCode();
        this.country = patient.getCountry();
        this.securityNumber = patient.getSecurityNumber();
        this.familyName = patient.getFamilyName();
        this.givenName = patient.getGivenName();
        this.telecom = patient.getTelecom();
        this.gender = patient.getGender();
        this.birthDate = patient.getBirthDate() != null ? patient.getBirthDate().toString() : "";
        this.maritalStatus = patient.getMaritalStatus();
        this.language = patient.getLanguage();
    }

    public static List<PatientResponse> fromPatients(List<ModelPatient> patients) {
        return patients.stream().map(patient -> new PatientResponse(
                patient.getId(),
                patient.getAddress(),
                patient.getCity(),
                patient.getState(),
                patient.getPostalCode(),
                patient.getCountry(),
                patient.getSecurityNumber(),
                patient.getFamilyName(),
                patient.getGivenName(),
                patient.getTelecom(),
                patient.getGender(),
                patient.getBirthDate() != null ? patient.getBirthDate().toString() : "",
                patient.getMaritalStatus(),
                patient.getLanguage())).collect(Collectors.toList());
    }

}