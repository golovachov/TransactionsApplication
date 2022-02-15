package com.transactionsapplication.core.service;

import com.transactionsapplication.core.data.entities.Account;
import com.transactionsapplication.core.data.entities.Transaction;
import com.transactionsapplication.core.data.objects.Response;
import com.transactionsapplication.core.data.objects.Status;
import com.transactionsapplication.core.data.objects.TransactionDto;
import com.transactionsapplication.core.data.repositories.AccountRepository;
import com.transactionsapplication.core.data.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final PlatformTransactionManager transactionManager;

    public ResponseEntity<Response> transaction(TransactionDto transactionDto) {
        UUID requestId;
        Integer accountFromId;
        Integer accountToId;
        Double amount;
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        try {
            requestId = UUID.fromString(transactionDto.getRequest_id());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder()
                    .result("ERROR")
                    .description("Not valid request_id")
                    .build());
        }
        if (transactionRepository.existsByRequestId(requestId))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder()
                    .result("ERROR")
                    .description("Transaction already exist")
                    .build());


        try {
            accountFromId = Integer.parseInt(transactionDto.getAccount_from());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder()
                    .result("ERROR")
                    .description("Not valid account_from")
                    .build());
        }
        if (accountRepository.findById(accountFromId).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder()
                    .result("ERROR")
                    .description("account_from not found")
                    .build());


        try {
            accountToId = Integer.parseInt(transactionDto.getAccount_to());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder()
                    .result("ERROR")
                    .description("Not valid account_to")
                    .build());
        }
        if (accountRepository.findById(accountToId).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder()
                    .result("ERROR")
                    .description("account_to not found")
                    .build());


        try {
            amount = Double.parseDouble(transactionDto.getAmount());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder()
                    .result("ERROR")
                    .description("not valid amount")
                    .build());
        }

        Account accountFrom = accountRepository.getById(accountFromId);
        if (accountFrom.getAmount() - amount < 0) {
            transactionRepository.save(Transaction.builder()
                    .requestId(requestId)
                    .accountFrom(accountFromId)
                    .accountTo(accountToId)
                    .initiator(transactionDto.getAccount_from())
                    .amount(amount)
                    .status(Status.ERROR.getId())
                    .build());
            return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                    .result("ERROR")
                    .description("not enough money")
                    .build());
        }

        Account accountTo = accountRepository.getById(accountToId);
        accountFrom.setAmount(accountFrom.getAmount() - amount);
        accountTo.setAmount(accountTo.getAmount() + amount);
        Integer id = transactionTemplate.execute(status -> {
            accountRepository.save(accountFrom);
            accountRepository.save(accountTo);
            Transaction transaction = Transaction.builder()
                    .requestId(requestId)
                    .accountFrom(accountFromId)
                    .accountTo(accountToId)
                    .initiator(transactionDto.getAccount_from())
                    .amount(amount)
                    .status(Status.SUCCESS.getId())
                    .build();
            transactionRepository.save(transaction);
            return transaction.getId();
        });

        if (id != null)
            return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                    .result("SUCCESS")
                    .build());
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                .result("ERROR")
                .description("Something went wrong")
                .build());
    }
}
