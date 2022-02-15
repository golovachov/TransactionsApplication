package com.transactionsapplication.core.data.objects;

import lombok.AllArgsConstructor;

import lombok.Getter;

@AllArgsConstructor
public enum Status {
    SUCCESS("Success", 1),
    ERROR("Error", 2);

    @Getter
    private String value;
    @Getter
    private Integer id;
}
