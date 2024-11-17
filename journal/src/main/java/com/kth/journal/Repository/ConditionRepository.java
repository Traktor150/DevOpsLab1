package com.kth.journal.Repository;

import com.kth.journal.domain.MedicalCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<MedicalCondition, Long> {
}
