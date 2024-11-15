package com.kth.journal.Controlers;

import com.kth.journal.Dto.RecipientResponse;
import com.kth.journal.Services.UserService;
import com.kth.journal.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class UserController {
    private final UserService userService;

    @GetMapping("/recipients")
    public ResponseEntity<List<RecipientResponse>> getRecipients() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName(); // Current authenticated user

        List<Account> recipients = userService.getRecipients(currentUserEmail);
        List<RecipientResponse> response = recipients.stream()
                .map(account -> new RecipientResponse(account.getId(), account.getName(), account.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
