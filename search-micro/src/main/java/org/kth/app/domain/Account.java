package org.kth.app.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String email;
    private String role;

    @OneToMany(mappedBy = "senderAccount")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "id.conversation")
    private List<Message> conversations;

    @OneToOne(mappedBy = "account")
    private Patient patient;

    @OneToOne(mappedBy = "account")
    private Practitioner practitioner;

    public Account() {
    }

    public Account(String username, String password, String email, String role) {
        this.name = username;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }
}
