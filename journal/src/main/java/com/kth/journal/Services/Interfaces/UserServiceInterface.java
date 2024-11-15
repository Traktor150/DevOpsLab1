package com.kth.journal.Services.Interfaces;

import com.kth.journal.domain.Account;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    boolean hasUserWithEmail(String email);
    Account saveUser(Account user);
    Optional<Account> getUserByEmail(String email);
    Optional<Account> validUsernameAndPassword(String email, String password);
    List<Account> getRecipients(String currentUserEmail);
}
