package com.kth.journal.Services.Interfaces;

import com.kth.journal.domain.Account;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    Optional<Account> getUserByEmail(String email);
    List<Account> getRecipients(String currentUserEmail);
}
