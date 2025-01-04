package com.kth.message;

import com.kth.message.Repository.AccountRepository;
import com.kth.message.Repository.MessageRepository;
import com.kth.message.Services.MessageService;
import com.kth.message.domain.Account;
import com.kth.message.domain.Message;
import com.kth.message.domain.MessageId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    private Account sender;
    private Account recipient;
    private Message testMessage;

    @BeforeEach
    void setUp() {
        sender = new Account();
        sender.setId(1L);
        sender.setName("Sender");

        recipient = new Account();
        recipient.setId(2L);
        recipient.setName("Recipient");

        MessageId messageId = new MessageId();
        messageId.setConversation(recipient);
        messageId.setTimestamp(new Timestamp(System.currentTimeMillis()));

        testMessage = new Message();
        testMessage.setId(messageId);
        testMessage.setSenderAccount(sender);
        testMessage.setMessage("Test message");
    }

    @Test
    void sendMessage_Success() {
        // Given
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(sender));
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.of(recipient));
        Mockito.when(messageRepository.save(Mockito.any(Message.class))).thenReturn(testMessage);

        // When
        Message result = messageService.sendMessage(1L, 2L, "Test message");

        // Then
        assertNotNull(result);
        assertEquals("Test message", result.getMessage());
        assertEquals(sender, result.getSenderAccount());
        assertEquals(recipient, result.getId().getConversation());
        Mockito.verify(messageRepository).save(Mockito.any(Message.class));
    }

    @Test
    void sendMessage_SenderNotFound() {
        // Given
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(RuntimeException.class, () ->
                messageService.sendMessage(1L, 2L, "Test message")
        );
        Mockito.verify(messageRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void getMessagesForAccount_Success() {
        // Given
        List<Message> messages = Collections.singletonList(testMessage);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(sender));
        Mockito.when(messageRepository.findByIdConversation(sender)).thenReturn(messages);

        // When
        List<Message> result = messageService.getMessagesForAccount(1L);

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test message", result.get(0).getMessage());
        Mockito.verify(messageRepository).findByIdConversation(sender);
    }

    @Test
    void getConversation_Success() {
        // Given
        List<Message> messages = Collections.singletonList(testMessage);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(sender));
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.of(recipient));
        Mockito.when(messageRepository.findMessagesBetweenAccounts(sender, recipient))
                .thenReturn(messages);

        // When
        List<Message> result = messageService.getConversation(1L, 2L);

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test message", result.get(0).getMessage());
        Mockito.verify(messageRepository).findMessagesBetweenAccounts(sender, recipient);
    }
}
