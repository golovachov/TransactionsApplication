package com.transactionsapplication.core.controller;

import com.transactionsapplication.core.data.objects.Response;
import com.transactionsapplication.core.data.objects.TransactionDto;
import com.transactionsapplication.core.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Response> transaction(
            @RequestBody TransactionDto transactionDto) {
        return transactionService.transaction(transactionDto);
    }
}
