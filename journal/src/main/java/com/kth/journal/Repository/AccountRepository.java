package com.kth.journal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kth.journal.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
