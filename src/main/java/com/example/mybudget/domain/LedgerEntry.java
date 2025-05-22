package com.example.mybudget.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LedgerEntry {
    private Long id;
    private Long userId;
    private String type;
    private int amount;
    private String memo;
    private LocalDateTime date;
    private LedgerEntry createdAt;
}
