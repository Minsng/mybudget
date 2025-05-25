package com.example.mybudget.mapper;

import com.example.mybudget.domain.Budget;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BudgetMapper {
    List<Budget> findByUserIdAndMonth(@Param("userId") Long userId, @Param("month") String month);

    Budget findOne(@Param("userId") Long userId,
                   @Param("categoryId") Long categoryId,
                   @Param("month") String month);

    void insertBudget(Budget budget);

    void updateBudget(Budget budget);
}
