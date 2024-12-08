package org.kth.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.kth.app.domain.Practitioner;
import org.kth.app.dto.SearchResponse;
import org.kth.app.repository.PractitionerRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PractitionerService {
    @Inject
    PractitionerRepository practitionerRepository;

    // Search practitioner by partial name
    public List<SearchResponse> searchPractitionersByPartialName(String name) {
        List<Practitioner> practitioners = practitionerRepository.findByPartialName(name);
        return practitioners.stream().map(practitioner -> new SearchResponse(practitioner.getId(), practitioner.getAccount().getId(), practitioner.getAccount().getName(), practitioner.getAccount().getEmail(), practitioner.getAccount().getRole())).collect(Collectors.toList());
    }
}
