package com.kth.message.Services;

import com.kth.message.Repository.AccountRepository;
import com.kth.message.Repository.MessageRepository;
import com.kth.message.Services.Interfaces.MessageServiceInterface;
import com.kth.message.domain.Account;
import com.kth.message.domain.Message;
import com.kth.message.domain.MessageId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService implements MessageServiceInterface {
    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;

    @Override
    // Skickar ett meddelande från en användare (patient eller personal) till en annan användare
    public Message sendMessage(Long senderId, Long recipientId, String content) {
        Account sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Account recipient = accountRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("Recipient not found"));

        Message message = new Message();
        message.setSenderAccount(sender);

        MessageId messageId = new MessageId();
        messageId.setConversation(recipient);
        messageId.setTimestamp(new Timestamp(System.currentTimeMillis()));

        message.setId(messageId);
        message.setMessage(content);

        return messageRepository.save(message);
    }

    @Override
    // Hämtar alla meddelanden för en användare, sorterade i kronologisk ordning
    public List<Message> getMessagesForAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Hämta meddelanden där användaren är mottagare, och sortera efter timestamp
        List<Message> messages = messageRepository.findByIdConversation(account);
        messages.sort(Comparator.comparing(msg -> msg.getId().getTimestamp()));

        return messages;
    }

    @Override
    // Hämta alla meddelanden i en konversation mellan två specifika användare
    public List<Message> getConversation(Long accountId1, Long accountId2) {
        Account account1 = accountRepository.findById(accountId1)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Account account2 = accountRepository.findById(accountId2)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Hämta alla meddelanden som skickats till eller från dessa två användare
        List<Message> messages = messageRepository.findMessagesBetweenAccounts(account1, account2);
        messages.sort(Comparator.comparing(msg -> msg.getId().getTimestamp()));

        return messages;
    }

    public Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account with ID " + accountId + " not found"));
    }
}
