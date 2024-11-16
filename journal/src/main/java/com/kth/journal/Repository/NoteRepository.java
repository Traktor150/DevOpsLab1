package com.kth.journal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kth.journal.domain.Account;
import com.kth.journal.domain.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByPatient(Account patient);

    List<Note> findByPatientAndCategory(Account patient, String category);

    List<Note> findByPatientAndAuthor(Account patient, Account author);

    List<Note> findByPatientAndAuthorAndCategory(Account patient, Account author, String category);

    List<Note> findByAuthor(Account author);

    List<Note> findByAuthorAndCategory(Account author, String category);

    List<Note> findByCategory(String category);

    List<Note> findByPatientAndCategoryAndAuthor(Account patient, String category, Account author);

}
