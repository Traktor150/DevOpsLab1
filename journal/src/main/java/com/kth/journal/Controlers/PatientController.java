package com.kth.journal.Controlers;

import com.kth.journal.Dto.*;
import com.kth.journal.Services.PatientService;
import com.kth.journal.Services.UserService;
import com.kth.journal.domain.Account;
import com.kth.journal.domain.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<PatientProfile> getPatientInfo() {
        // Retrieve the logged-in user's email
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = auth.getName();

        Optional<Account> account = userService.getUserByEmail(loggedInEmail);

        if(account.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Account acc = account.get();

        Patient patient = patientService.getPatientByAccountId(acc.getId());

        if(patient.getAccount() == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(toDTO(patient));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = auth.getName();

        Optional<Account> account = userService.getUserByEmail(loggedInEmail);

        if(account.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Account acc = account.get();

        if(Objects.equals(acc.getRole(), "PATIENT")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients.stream().map(patient -> new PatientResponse(patient.getId(), patient.getAccount().getId(), patient.getAccount().getName(), patient.getAccount().getEmail())).collect(Collectors.toList()));
    }

    @GetMapping("/single/{accountId}")
    public ResponseEntity<PatientProfile> getPatientById(@PathVariable Long accountId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = auth.getName();

        Optional<Account> account = userService.getUserByEmail(loggedInEmail);

        if(account.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Account acc = account.get();

        if(!Objects.equals(acc.getRole(), "DOCTOR")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Patient patient = patientService.getPatientByAccountId(accountId);
        return ResponseEntity.ok(toDTO(patient));
    }

    private PatientProfile toDTO(Patient patient) {
        PatientProfile patientDTO = new PatientProfile();
        patientDTO.setId(patient.getId());

        Account account = patient.getAccount();
        AccountResponse accountDTO = new AccountResponse(account.getId(), account.getName(), account.getEmail(), account.getRole());
        patientDTO.setAccount(accountDTO);

        List<ConditionResponse> conditions = patient.getConditions().stream().map(condition -> new ConditionResponse(condition.getId(), condition.getName(), condition.getDescription())).collect(Collectors.toList());
        patientDTO.setConditions(conditions);

        List<NoteResponse> notes = patient.getNotes().stream().map(note -> new NoteResponse(note.getId(), note.getContent())).collect(Collectors.toList());
        patientDTO.setNotes(notes);

        return patientDTO;
    }
}
