package com.kth.journal.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String email;
    private String role;
    private String SSN;

    @OneToMany(mappedBy = "senderAccount")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "id.conversation")
    private List<Message> conversations;

    public Account() {
    }

    public Account(String username, String password, String email, String role, String SSN) {
        this.name = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.SSN = SSN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getConversations() {
        return conversations;
    }

    public void setConversations(List<Message> conversations) {
        this.conversations = conversations;
    }

}
