package com.kth.journal.Services.Interfaces;

import com.kth.journal.domain.Note;

public interface NoteServiceInterface {
    Note createNote(Long patientId, Long practitionerId, String noteContent);
}
