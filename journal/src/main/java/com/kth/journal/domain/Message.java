package com.kth.journal.domain;

import jakarta.persistence.Entity;

@Entity
public class Message {

    private Account patientAccount;

    private Account senderAccount;

    private String message;

    public Message() {
    }

    public Message(String message, Account patientAccount, Account senderAccount) {
        this.message = message;
        this.patientAccount = patientAccount;
        this.senderAccount = senderAccount;
    }

    public String getMessage() {
        return message;
    }

    public Account getSenderAccount() {
        return patientAccount;
    }

    public Account getPatientAccount() {
        return senderAccount;
    }

}
