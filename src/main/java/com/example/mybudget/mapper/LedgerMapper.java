package com.example.mybudget.mapper;

import com.example.mybudget.domain.LedgerEntry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LedgerMapper {
    List<LedgerEntry> findByUserId(long userId);
    void insertLedgerEntry(LedgerEntry ledgerEntry);
}
