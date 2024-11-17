package com.kth.journal.Services;

import com.kth.journal.Repository.NoteRepository;
import com.kth.journal.Repository.PatientRepository;
import com.kth.journal.Repository.PractitionerRepository;
import com.kth.journal.Services.Interfaces.NoteServiceInterface;
import com.kth.journal.domain.Note;
import com.kth.journal.domain.Patient;
import com.kth.journal.domain.Practitioner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class NoteService implements NoteServiceInterface {
    private final NoteRepository noteRepository;
    private final PatientRepository patientRepository;
    private final PractitionerRepository practitionerRepository;

    @Override
    public Note createNote(Long patientId, Long practitionerId, String noteContent) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Practitioner practitioner = practitionerRepository.findById(practitionerId)
                .orElseThrow(() -> new RuntimeException("Practitioner not found"));

        Note note = new Note();
        note.setPatient(patient);
        note.setPractitioner(practitioner);
        note.setContent(noteContent);
        note.setCreatedAt(new Date());

        return noteRepository.save(note);
    }
}