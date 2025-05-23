package com.example.mybudget.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LedgerEntry {
    private Long id;
    private Long userId;
    private String type;
    private int amount;
    private String memo;
    private LocalDate date;
    private LocalDateTime createdAt;

    private Long categoryId;
    private String categoryName;
}
