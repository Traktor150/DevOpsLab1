package com.kth.journal.Services.Interfaces;

import com.kth.journal.domain.Patient;

import java.util.List;

public interface PatientServiceInterface {
    Patient createPatient(Long accountId);
    List<Patient> getAllPatients();
}
