package org.kth.app.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.kth.app.domain.Practitioner;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PractitionerRepository {
    @Inject
    JPAStreamer jpaStreamer;

    // Search for practitioner by partial name match
    public List<Practitioner> findByPartialName(String name) {
        return jpaStreamer.stream(Practitioner.class)
                .filter(practitioner -> practitioner.getAccount().getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
