package com.kth.journal.Dto;

import java.util.List;

import com.kth.journal.domain.Remote.ModelPatient;

import io.micrometer.common.lang.NonNull;
import lombok.Getter;

@Getter
public class PatientsResponse {

    private final List<PatientResponse> patients;

    public PatientsResponse(List<ModelPatient> patients) {
        this.patients = PatientResponse.fromPatients(patients);
    }
}
