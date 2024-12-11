package org.kth.app.service;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.kth.app.domain.Account;
import org.kth.app.domain.Patient;
import org.kth.app.dto.SearchResponse;
import org.kth.app.repository.AccountRepository;
import org.kth.app.repository.PatientRepository;

@ApplicationScoped
public class PatientService {
    @Inject
    PatientRepository patientRepository;
    @Inject
    AccountRepository accountRepository;

    // Search patients by partial name
    public Multi<SearchResponse> searchPatientsByPartialName(String name) {
        return patientRepository.findByPartialName(name)
                .onItem().transformToUniAndConcatenate(patient ->
                        accountRepository.findById(patient.getAccount().getId())
                                .onItem().transform(account -> mapToSearchResponse(patient, account))
                );
    }

    // Search patients by partial email
    public Multi<SearchResponse> searchPatientsByPartialEmail(String email) {
        return patientRepository.findByPartialEmail(email)
                .onItem().transformToUniAndConcatenate(patient ->
                        accountRepository.findById(patient.getAccount().getId())
                                .onItem().transform(account -> mapToSearchResponse(patient, account))
                );
    }

    // Search patients by partial condition name
    public Multi<SearchResponse> searchPatientsByPartialCondition(String condition) {
        return patientRepository.findByPartialCondition(condition)
                .onItem().transformToUniAndConcatenate(patient ->
                        accountRepository.findById(patient.getAccount().getId())
                                .onItem().transform(account -> mapToSearchResponse(patient, account))
                );
    }

    private SearchResponse mapToSearchResponse(Patient patient, Account account) {
        return new SearchResponse(
                patient.getId(),
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.getRole()
        );
    }
}

