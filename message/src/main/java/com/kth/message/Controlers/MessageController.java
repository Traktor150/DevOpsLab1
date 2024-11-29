package com.kth.message.Controlers;

import com.kth.message.Dto.MessageRequest;
import com.kth.message.Dto.MessageResponse;
import com.kth.message.Services.MessageService;
import com.kth.message.domain.Account;
import com.kth.message.domain.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    private void isAuthorized(Long senderId) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName(); // Current authenticated user

        Account sender = messageService.findAccountById(senderId);
        if (!currentUserEmail.equals(sender.getEmail())) {
            throw new AccessDeniedException("Unauthorized account.");
        }
    }

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest request) throws AccessDeniedException {
        isAuthorized(request.getSenderId());
        Message message = messageService.sendMessage(request.getSenderId(), request.getRecipientId(), request.getMessage());
        return ResponseEntity.ok(
                new MessageResponse(
                        message.getSenderAccount().getId(),
                        message.getSenderAccount().getName(),
                        message.getId().getConversation().getId(),
                        message.getMessage(),
                        message.getId().getTimestamp()
                )
        );
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<MessageResponse>> getMessagesForAccount(@PathVariable Long accountId) throws AccessDeniedException {
        isAuthorized(accountId);
        List<Message> messages = messageService.getMessagesForAccount(accountId);
        List<MessageResponse> responses = messages.stream()
                .map(msg -> new MessageResponse(
                        msg.getSenderAccount().getId(),
                        msg.getSenderAccount().getName(),
                        msg.getId().getConversation().getId(),
                        msg.getMessage(),
                        msg.getId().getTimestamp()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{accountId1}/conversation/{accountId2}")
    public ResponseEntity<List<MessageResponse>> getConversation(@PathVariable Long accountId1, @PathVariable Long accountId2) throws AccessDeniedException {
        isAuthorized(accountId1);
        List<Message> conversation = messageService.getConversation(accountId1, accountId2);
        List<MessageResponse> responses = conversation.stream()
                .map(msg -> new MessageResponse(
                        msg.getSenderAccount().getId(),
                        msg.getSenderAccount().getName(),
                        msg.getId().getConversation().getId(),
                        msg.getMessage(),
                        msg.getId().getTimestamp()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
