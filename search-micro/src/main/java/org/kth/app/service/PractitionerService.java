package org.kth.app.service;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.kth.app.domain.Account;
import org.kth.app.domain.Practitioner;
import org.kth.app.dto.SearchResponse;
import org.kth.app.repository.AccountRepository;
import org.kth.app.repository.PractitionerRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PractitionerService {
    @Inject
    PractitionerRepository practitionerRepository;

    @Inject
    AccountRepository accountRepository; // Assuming an AccountRepository is available for fetching Account details

    // Search practitioners by partial name
    public Multi<SearchResponse> searchPractitionersByPartialName(String name) {
        return practitionerRepository.findByPartialName(name)
                .onItem().transformToUniAndConcatenate(practitioner ->
                        accountRepository.findById(practitioner.getAccount().getId())
                                .onItem().transform(account -> mapToSearchResponse(practitioner, account))
                );
    }

    private SearchResponse mapToSearchResponse(Practitioner practitioner, Account account) {
        return new SearchResponse(
                practitioner.getId(),
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.getRole()
        );
    }
}
