package com.kth.auth.Services;

import com.kth.auth.Repository.AccountRepository;
import com.kth.auth.Services.Interfaces.UserServiceInterface;
import com.kth.auth.domain.Account;
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
}
