package com.transactionsapplication.core.data.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private String request_id;
    private String account_from;
    private String account_to;
    private String amount;

}
