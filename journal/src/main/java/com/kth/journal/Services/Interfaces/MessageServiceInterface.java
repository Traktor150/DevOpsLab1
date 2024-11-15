package com.kth.journal.Services.Interfaces;

import com.kth.journal.domain.Account;
import com.kth.journal.domain.Message;

import java.util.List;

public interface MessageServiceInterface {
    Message sendMessage(Long senderId, Long recipientId, String content);
    List<Message> getMessagesForAccount(Long accountId);
    List<Message> getConversation(Long accountId1, Long accountId2);
    Account findAccountById(Long accountId);
}
