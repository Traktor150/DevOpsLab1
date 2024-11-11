package com.kth.journal.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Message {

    @EmbeddedId
    private MessageId id;

    private Account senderAccount;

    private String message;

    public Message() {
    }

    public Message(MessageId messageId, Account patientAccount, String message) {
        this.id = messageId;
        this.senderAccount = senderAccount;
        this.message = message;
    }

    public MessageId getId() {
        return id;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public String getMessage() {
        return message;
    }

    public void setId(MessageId messageId) {
        this.id = messageId;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
