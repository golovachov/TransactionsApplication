package com.transactionsapplication.core.data.repositories;

import com.transactionsapplication.core.data.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    boolean existsByRequestId(UUID requestedId);
}
