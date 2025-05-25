package com.example.mybudget.controller;

import com.example.mybudget.domain.Budget;
import com.example.mybudget.security.CustomUserDetails;
import com.example.mybudget.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budget")
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public List<Budget> getBudgets(@RequestParam String month,
                                   @AuthenticationPrincipal CustomUserDetails user) {
        return budgetService.getBudgets(user.getUser().getId(), month);
    }

    @PostMapping
    public void saveBudgets(@RequestBody List<Budget> budgets,
                            @AuthenticationPrincipal CustomUserDetails user) {
        budgetService.saveBudgets(user.getUser().getId(), budgets);
    }
}
