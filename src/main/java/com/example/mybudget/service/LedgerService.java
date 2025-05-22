package com.example.mybudget.service;

import com.example.mybudget.domain.LedgerEntry;
import com.example.mybudget.dto.LedgerEntryInsertRequest;
import com.example.mybudget.mapper.LedgerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerMapper ledgerMapper;

    public List<LedgerEntry> getEntriesByUserId(Long userId) {
        return ledgerMapper.findByUserId(userId);
    }

    public void addEntry(Long userId, LedgerEntryInsertRequest request) {
        LedgerEntry entry = new LedgerEntry();
        entry.setUserId(userId);
        entry.setType(request.getType());
        entry.setAmount(request.getAmount());
        entry.setMemo(request.getMemo());
        entry.setDate(request.getDate());

        ledgerMapper.insertLedgerEntry(entry);
    }
}
