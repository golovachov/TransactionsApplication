package com.transactionsapplication.core.data.repositories;

import com.transactionsapplication.core.data.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
