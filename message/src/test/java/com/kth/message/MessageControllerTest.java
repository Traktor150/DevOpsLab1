package com.kth.message;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kth.message.Controlers.MessageController;
import com.kth.message.Dto.MessageRequest;
import com.kth.message.Dto.MessageResponse;
import com.kth.message.Services.MessageService;
import com.kth.message.domain.Account;
import com.kth.message.domain.Message;
import com.kth.message.domain.MessageId;
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

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt jwt;

    private static final String TEST_EMAIL = "test@example.com";
    private static final Long SENDER_ID = 1L;
    private static final Long RECIPIENT_ID = 2L;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getClaimAsString("email")).thenReturn(TEST_EMAIL);
    }

    @Test
    void sendMessage_Success() throws AccessDeniedException {
        // Arrange
        MessageRequest request = new MessageRequest();
        request.setSenderId(SENDER_ID);
        request.setRecipientId(RECIPIENT_ID);
        request.setMessage("Test message");

        Account sender = new Account();
        sender.setId(SENDER_ID);
        sender.setEmail(TEST_EMAIL);
        sender.setName("Sender Name");

        Message message = new Message();
        message.setSenderAccount(sender);
        MessageId messageId = new MessageId();
        messageId.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Account recipientAccount = new Account();
        recipientAccount.setId(RECIPIENT_ID);
        messageId.setConversation(recipientAccount);
        message.setId(messageId);
        message.setMessage("Test message");

        when(messageService.findAccountById(SENDER_ID)).thenReturn(sender);
        when(messageService.sendMessage(SENDER_ID, RECIPIENT_ID, "Test message")).thenReturn(message);

        // Act
        ResponseEntity<MessageResponse> response = messageController.sendMessage(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().senderId()).isEqualTo(SENDER_ID);
        assertThat(response.getBody().message()).isEqualTo("Test message");
        verify(messageService).sendMessage(SENDER_ID, RECIPIENT_ID, "Test message");
    }

    @Test
    void sendMessage_UnauthorizedAccess() {
        // Arrange
        MessageRequest request = new MessageRequest();
        request.setSenderId(SENDER_ID);
        request.setRecipientId(RECIPIENT_ID);
        request.setMessage("Test message");

        Account sender = new Account();
        sender.setId(SENDER_ID);
        sender.setEmail("different@example.com");  // Different email than JWT

        when(messageService.findAccountById(SENDER_ID)).thenReturn(sender);

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> messageController.sendMessage(request));
    }

    @Test
    void getMessagesForAccount_Success() throws AccessDeniedException {
        // Arrange
        Account account = new Account();
        account.setId(SENDER_ID);
        account.setEmail(TEST_EMAIL);

        List<Message> messages = createTestMessages();

        when(messageService.findAccountById(SENDER_ID)).thenReturn(account);
        when(messageService.getMessagesForAccount(SENDER_ID)).thenReturn(messages);

        // Act
        ResponseEntity<List<MessageResponse>> response = messageController.getMessagesForAccount(SENDER_ID);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        verify(messageService).getMessagesForAccount(SENDER_ID);
    }

    @Test
    void getConversation_Success() throws AccessDeniedException {
        // Arrange
        Account account = new Account();
        account.setId(SENDER_ID);
        account.setEmail(TEST_EMAIL);

        List<Message> conversation = createTestMessages();

        when(messageService.findAccountById(SENDER_ID)).thenReturn(account);
        when(messageService.getConversation(SENDER_ID, RECIPIENT_ID)).thenReturn(conversation);

        // Act
        ResponseEntity<List<MessageResponse>> response =
                messageController.getConversation(SENDER_ID, RECIPIENT_ID);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        verify(messageService).getConversation(SENDER_ID, RECIPIENT_ID);
    }

    private List<Message> createTestMessages() {
        Account sender = new Account();
        sender.setId(SENDER_ID);
        sender.setName("Sender Name");

        Account recipient = new Account();
        recipient.setId(RECIPIENT_ID);

        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Message message = new Message();
            message.setSenderAccount(sender);
            MessageId messageId = new MessageId();
            messageId.setTimestamp(new Timestamp(System.currentTimeMillis()));
            messageId.setConversation(recipient);
            message.setId(messageId);
            message.setMessage("Test message " + i);
            messages.add(message);
        }
        return messages;
    }
}