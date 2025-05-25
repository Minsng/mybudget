package com.example.mybudget.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Budget {
    private Long id;
    private Long userId;
    private Long categoryId;
    private String month;
    private Integer amount;
    private LocalDateTime createdAt;

    private String categoryName;
    private String categoryType;
}
