package com.kth.journal.Services;

import com.kth.journal.Repository.AccountRepository;
import com.kth.journal.Services.Interfaces.UserServiceInterface;
import com.kth.journal.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserServiceInterface {
    private final AccountRepository accRepository;

    @Override
    public Optional<Account> getUserByEmail(String email) {
        return accRepository.findByEmail(email);
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
}
