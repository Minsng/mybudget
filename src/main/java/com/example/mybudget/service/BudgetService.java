package com.example.mybudget.service;

import com.example.mybudget.domain.Budget;
import com.example.mybudget.mapper.BudgetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetMapper budgetMapper;

    public List<Budget> getBudgets(Long userId, String month) {
        return budgetMapper.findByUserIdAndMonth(userId, month);
    }

    public void saveBudgets(Long userId, List<Budget> budgets) {
        for (Budget budget : budgets) {
            budget.setUserId(userId);
            Budget existing = budgetMapper.findOne(userId, budget.getCategoryId(), budget.getMonth());

            if (existing == null) {
                budgetMapper.insertBudget(budget);
            } else {
                budgetMapper.updateBudget(budget);
            }
        }
    }
}
