package com.example.mybudget.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LedgerEntryInsertRequest {
    private String type;
    private int amount;
    private String memo;
    private LocalDate date;

    private Long categoryId;
}
