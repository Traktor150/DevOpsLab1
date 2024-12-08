package org.kth.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.kth.app.domain.Patient;
import org.kth.app.dto.SearchResponse;
import org.kth.app.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PatientService {
    @Inject
    PatientRepository patientRepository;

    // Search patients by partial name
    public List<SearchResponse> searchPatientsByPartialName(String name) {
        List<Patient> patients = patientRepository.findByPartialName(name);
        return patients.stream().map(patient -> new SearchResponse(patient.getId(), patient.getAccount().getId(), patient.getAccount().getName(), patient.getAccount().getEmail(), patient.getAccount().getRole())).collect(Collectors.toList());
    }

    // Search patients by partial email
    public List<SearchResponse> searchPatientsByPartialEmail(String email) {
        List<Patient> patients = patientRepository.findByPartialEmail(email);
        return patients.stream().map(patient -> new SearchResponse(patient.getId(), patient.getAccount().getId(), patient.getAccount().getName(), patient.getAccount().getEmail(), patient.getAccount().getRole())).collect(Collectors.toList());
    }

    // Search patients by partial condition name
    public List<SearchResponse> searchPatientsByPartialCondition(String condition) {
        List<Patient> patients =  patientRepository.findByPartialCondition(condition);
        return patients.stream().map(patient -> new SearchResponse(patient.getId(), patient.getAccount().getId(), patient.getAccount().getName(), patient.getAccount().getEmail(), patient.getAccount().getRole())).collect(Collectors.toList());
    }
}

