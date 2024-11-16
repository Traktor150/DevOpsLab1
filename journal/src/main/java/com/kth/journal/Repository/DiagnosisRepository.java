package com.kth.journal.Repository;

import com.kth.journal.domain.Diagnosis;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    Diagnosis findByCode(String code);

    Diagnosis findByDisplay(String display);

    Diagnosis findBySystem(String system);

    Diagnosis findByTimestamp(Date timestamp);

    Diagnosis findByAuthor(String author);

    Diagnosis findByCodeAndDisplay(String code, String display);

    Diagnosis findByCodeAndSystem(String code, String system);

    Diagnosis findByCodeAndTimestamp(String code, Date timestamp);

    Diagnosis findByCodeAndAuthor(String code, String author);

    Diagnosis findByDisplayAndSystem(String display, String system);

    Diagnosis findByDisplayAndTimestamp(String display, Date timestamp);

    Diagnosis findByDisplayAndAuthor(String display, String author);

    Diagnosis findBySystemAndTimestamp(String system, Date timestamp);

    Diagnosis findBySystemAndAuthor(String system, String author);

    Diagnosis findByCodeAndDisplayAndSystem(String code, String display, String system);

    Diagnosis findByCodeAndDisplayAndTimestamp(String code, String display, Date timestamp);

    Diagnosis findByCodeAndDisplayAndAuthor(String code, String display, String author);

    Diagnosis findByCodeAndSystemAndTimestamp(String code, String system, Date timestamp);

    Diagnosis findByCodeAndSystemAndAuthor(String code, String system, String author);

    Diagnosis findByDisplayAndSystemAndTimestamp(String display, String system, Date timestamp);

    Diagnosis findByDisplayAndSystemAndAuthor(String display, String system, String author);

    Diagnosis findByCodeAndDisplayAndSystemAndTimestamp(String code, String display, String system,
            Date timestamp);

    Diagnosis findByCodeAndDisplayAndSystemAndAuthor(String code, String display, String system, String author);
}
