package com.kth.journal.Dto;

import java.util.List;
import java.util.stream.Collectors;

import com.kth.journal.domain.Remote.ModelPatient;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Getter
public class PatientsResponse {

    private final List<PatientResponse> patients;

    public PatientsResponse(List<ModelPatient> patients) {
        this.patients = patients.stream().map(PatientResponse::new).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class PatientResponse {

        private final String id;
        private final String securityNumber;
        private final String familyName;
        private final String givenName;
        private final String telecom;
        private final String language;

        public PatientResponse(ModelPatient patient) {
            this.id = patient.getId();
            this.securityNumber = patient.getSecurityNumber();
            this.familyName = patient.getFamilyName();
            this.givenName = patient.getGivenName();
            this.telecom = patient.getTelecom();
            this.language = patient.getLanguage();
        }
    }
}
