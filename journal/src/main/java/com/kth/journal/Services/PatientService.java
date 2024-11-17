package com.kth.journal.Services;

import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import com.kth.journal.Repository.AccountRepository;
import com.kth.journal.Repository.PatientRepository;
import com.kth.journal.Services.Interfaces.PatientServiceInterface;
import com.kth.journal.domain.Account;
import com.kth.journal.domain.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService implements PatientServiceInterface {

    private final PatientRepository patientRepository;
    private final AccountRepository accountRepository;

    @Override
    public Patient createPatient(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!"PATIENT".equals(account.getRole())) {
            throw new IllegalArgumentException("Account role must be PATIENT");
        }

        Patient patient = new Patient();
        patient.setAccount(account);
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientByAccountId(Long accountId) {
        return patientRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for account ID: " + accountId));
    }
}
