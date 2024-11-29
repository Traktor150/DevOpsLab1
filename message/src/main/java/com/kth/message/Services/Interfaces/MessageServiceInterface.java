package com.kth.message.Services.Interfaces;

import com.kth.message.domain.Account;
import com.kth.message.domain.Message;

import java.util.List;

public interface MessageServiceInterface {
    Message sendMessage(Long senderId, Long recipientId, String content);
    List<Message> getMessagesForAccount(Long accountId);
    List<Message> getConversation(Long accountId1, Long accountId2);
    Account findAccountById(Long accountId);
}
