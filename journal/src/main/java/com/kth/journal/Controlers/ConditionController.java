package com.kth.journal.Controlers;

import com.kth.journal.Dto.ConditionRequest;
import com.kth.journal.Dto.NoteRequest;
import com.kth.journal.Services.ConditionService;
import com.kth.journal.Services.UserService;
import com.kth.journal.domain.Account;
import com.kth.journal.domain.MedicalCondition;
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
@RequestMapping("/api/conditions")
public class ConditionController {
    private final ConditionService conditionService;
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<String> createNote(@Valid @RequestBody ConditionRequest request) {
        // Retrieve the logged-in user's email
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = auth.getName();

        // Fetch the practitioner linked to this account
        Optional<Account> account = userService.getUserByEmail(loggedInEmail);

        if(account.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Account acc = account.get();

        if(acc.getPractitioner() == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        MedicalCondition condition = conditionService.createCondition(request.getPatientId(), acc.getPractitioner().getId(), request.getDiagnosisName(), request.getDiagnosisDesc());
        return ResponseEntity.ok("Condition created successfully");
    }
}
