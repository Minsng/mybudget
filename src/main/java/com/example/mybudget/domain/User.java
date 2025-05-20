package com.example.mybudget.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private LocalDate createdAt;
}
