package com.kth.journal.Services;

import com.kth.journal.Repository.AccountRepository;
import com.kth.journal.Services.Interfaces.UserServiceInterface;
import com.kth.journal.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserServiceInterface {
    private final AccountRepository accRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean hasUserWithEmail(String email) {
        return accRepository.existsByEmail(email);
    }

    @Override
    public Account saveUser(Account user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return accRepository.save(user);
    }

    @Override
    public Optional<Account> getUserByEmail(String email) {
        return accRepository.findByEmail(email);
    }

    @Override
    public Optional<Account> validUsernameAndPassword(String email, String password) {
        return getUserByEmail(email).filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    @Override
    public List<Account> getRecipients(String currentUserEmail) {
        Optional<Account> optionalUser = getUserByEmail(currentUserEmail);
        if (optionalUser.isEmpty())
            throw new RuntimeException("User doesn't exists");

        Account user = optionalUser.get();

        if (Objects.equals(user.getRole(), "PATIENT"))
            return accRepository.findByRoleIn(List.of("DOCTOR", "STAFF"));

        return accRepository.findByRoleIn(List.of("PATIENT"));
    }

    public boolean userIsDoctor(String email) {
        return getUserByEmail(email).map(user -> user.getRole().equals("doctor")).orElse(false);
    }
}
