package org.kth.app.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.kth.app.domain.Patient;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PatientRepository {
    @Inject
    JPAStreamer jpaStreamer; // Inject JPAStreamer

    @PersistenceContext
    private EntityManager entityManager;

    // Search for patients by partial name match
    public List<Patient> findByPartialName(String name) {
        return jpaStreamer.stream(Patient.class)
                .filter(patient -> patient.getAccount().getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Search for patients by partial email match
    public List<Patient> findByPartialEmail(String email) {
        return jpaStreamer.stream(Patient.class)
                .filter(patient -> patient.getAccount().getEmail().toLowerCase().contains(email.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Search for patients by partial condition name match
    public List<Patient> findByPartialCondition(String conditionName) {
        String sql = "SELECT * FROM patient p WHERE EXISTS (SELECT 1 FROM medical_condition mc WHERE mc.patient_id = p.id AND LOWER(mc.name) LIKE :conditionName)";

        Query query = entityManager.createNativeQuery(sql, Patient.class);
        query.setParameter("conditionName", "%" + conditionName.toLowerCase() + "%");

        return query.getResultList();
    }
}

