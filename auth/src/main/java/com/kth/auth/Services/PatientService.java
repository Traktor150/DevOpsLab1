package com.kth.auth.Services;

import com.kth.auth.Repository.AccountRepository;
import com.kth.auth.Repository.PatientRepository;
import com.kth.auth.Services.Interfaces.PatientServiceInterface;
import com.kth.auth.domain.Account;
import com.kth.auth.domain.Patient;
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
}
