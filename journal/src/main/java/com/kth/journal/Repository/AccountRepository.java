package com.kth.journal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kth.journal.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Account> findByRoleIn(List<String> roles);
}
