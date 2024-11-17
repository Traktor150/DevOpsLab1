package com.kth.journal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kth.journal.domain.Account;
import com.kth.journal.domain.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
