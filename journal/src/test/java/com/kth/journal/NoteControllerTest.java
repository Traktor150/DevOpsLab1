package com.kth.journal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.kth.journal.Controlers.NoteController;
import com.kth.journal.Dto.NoteRequest;
import com.kth.journal.Services.NoteService;
import com.kth.journal.Services.UserService;
import com.kth.journal.domain.Account;
import com.kth.journal.domain.Note;
import com.kth.journal.domain.Practitioner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @Mock
    private UserService userService;

    @InjectMocks
    private NoteController noteController;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt jwt;

    private static final String TEST_EMAIL = "doctor@example.com";
    private static final Long PATIENT_ID = 1L;
    private static final Long PRACTITIONER_ID = 1L;
    private static final String NOTE_CONTENT = "Test note content";

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getClaimAsString("email")).thenReturn(TEST_EMAIL);
    }

    @Test
    void createNote_Success() {
        // Arrange
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setPatientId(PATIENT_ID);
        noteRequest.setNoteContent(NOTE_CONTENT);

        Account practitionerAccount = createPractitionerAccount();
        Note createdNote = createNote();

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(practitionerAccount));
        when(noteService.createNote(PATIENT_ID, PRACTITIONER_ID, NOTE_CONTENT)).thenReturn(createdNote);

        // Act
        ResponseEntity<String> response = noteController.createNote(noteRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Note created successfully");
        verify(noteService).createNote(PATIENT_ID, PRACTITIONER_ID, NOTE_CONTENT);
    }

    @Test
    void createNote_UserNotFound() {
        // Arrange
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setPatientId(PATIENT_ID);
        noteRequest.setNoteContent(NOTE_CONTENT);

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = noteController.createNote(noteRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        verify(noteService, never()).createNote(any(), any(), any());
    }

    @Test
    void createNote_NotPractitioner() {
        // Arrange
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setPatientId(PATIENT_ID);
        noteRequest.setNoteContent(NOTE_CONTENT);

        Account nonPractitionerAccount = new Account();
        nonPractitionerAccount.setId(1L);
        nonPractitionerAccount.setEmail(TEST_EMAIL);
        nonPractitionerAccount.setRole("PATIENT");

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(nonPractitionerAccount));

        // Act
        ResponseEntity<String> response = noteController.createNote(noteRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        verify(noteService, never()).createNote(any(), any(), any());
    }

    @Test
    void createNote_ServiceThrowsException() {
        // Arrange
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setPatientId(PATIENT_ID);
        noteRequest.setNoteContent(NOTE_CONTENT);

        Account practitionerAccount = createPractitionerAccount();

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(practitionerAccount));
        when(noteService.createNote(PATIENT_ID, PRACTITIONER_ID, NOTE_CONTENT))
                .thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> noteController.createNote(noteRequest));
    }

    private Account createPractitionerAccount() {
        Practitioner practitioner = new Practitioner();
        practitioner.setId(PRACTITIONER_ID);

        Account account = new Account();
        account.setId(1L);
        account.setEmail(TEST_EMAIL);
        account.setRole("DOCTOR");
        account.setPractitioner(practitioner);

        return account;
    }

    private Note createNote() {
        Note note = new Note();
        note.setId(1L);
        note.setContent(NOTE_CONTENT);
        note.setCreatedAt(new Date());
        return note;
    }
}
