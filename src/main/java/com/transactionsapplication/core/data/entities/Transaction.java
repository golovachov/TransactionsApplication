package com.transactionsapplication.core.data.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private UUID requestId;

    @Column
    private Integer accountFrom;

    @Column
    private Integer accountTo;

    @Column
    private Double amount;

    @Column
    private String initiator;

    @Column
    private Integer status;
}
