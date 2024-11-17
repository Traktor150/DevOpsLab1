package com.kth.journal.Controlers;

import com.kth.journal.Dto.NoteRequest;
import com.kth.journal.Services.NoteService;
import com.kth.journal.Services.UserService;
import com.kth.journal.domain.Account;
import com.kth.journal.domain.Note;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<String> createNote(@Valid @RequestBody NoteRequest noteRequest) {

        // Retrieve the logged-in user's email
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = auth.getName();

        // Fetch the practitioner linked to this account
        Optional<Account> account = userService.getUserByEmail(loggedInEmail);

        if(account.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Account acc = account.get();

        if(acc.getPractitioner() == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Create the note
        Note note = noteService.createNote(noteRequest.getPatientId(), acc.getPractitioner().getId(), noteRequest.getNoteContent());
        return ResponseEntity.ok("Note created successfully");
    }
}
