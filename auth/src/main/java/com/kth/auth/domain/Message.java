package com.kth.auth.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Message {

    @EmbeddedId
    private MessageId id;

    @ManyToOne
    private Account senderAccount;

    private String message;

    public Message() {
    }

    public Message(MessageId messageId, Account senderAccount, String message) {
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
