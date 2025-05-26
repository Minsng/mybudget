package com.example.mybudget.dto;

import lombok.Data;

@Data
public class LedgerReportResponse {
    private String category;
    private int amount;
    private String type;
}
