package com.kth.auth.Services.Interfaces;

import com.kth.auth.domain.Patient;

import java.util.List;

public interface PatientServiceInterface {
    Patient createPatient(Long accountId);
}
