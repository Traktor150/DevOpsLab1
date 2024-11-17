package com.kth.journal.Services;

import com.kth.journal.Repository.AccountRepository;
import com.kth.journal.Repository.PractitionerRepository;
import com.kth.journal.Services.Interfaces.PractitionerServiceInterface;
import com.kth.journal.domain.Account;
import com.kth.journal.domain.Practitioner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PractitionerService implements PractitionerServiceInterface {
    private final PractitionerRepository practitionerRepository;
    private final AccountRepository accountRepository;

    @Override
    public Practitioner createPractitioner(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if ("PATIENT".equals(account.getRole())) {
            throw new IllegalArgumentException("Account role must be DOCTOR or STUFF");
        }

        Practitioner patient = new Practitioner();
        patient.setAccount(account);
        return practitionerRepository.save(patient);
    }
}
