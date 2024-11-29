package com.kth.journal.Services.Interfaces;

import com.kth.journal.domain.Patient;

import java.util.List;

public interface PatientServiceInterface {
    List<Patient> getAllPatients();
    Patient getPatientByAccountId(Long accountId);
}
