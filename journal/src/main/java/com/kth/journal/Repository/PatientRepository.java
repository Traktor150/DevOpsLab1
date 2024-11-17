package com.kth.journal.Repository;

import com.kth.journal.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByAccountId(Long accountId);
}
