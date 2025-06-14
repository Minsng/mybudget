package com.example.mybudget.service;

import com.example.mybudget.domain.LedgerEntry;
import com.example.mybudget.dto.LedgerEntryInsertRequest;
import com.example.mybudget.dto.LedgerReportResponse;
import com.example.mybudget.mapper.LedgerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerMapper ledgerMapper;

    public List<LedgerEntry> getEntriesByUserId(Long userId, String start, String end) {
        return ledgerMapper.findByUserIdAndDateRange(userId, start, end);
    }

    public void addLedgerEntry(Long userId, LedgerEntryInsertRequest request) {
        LedgerEntry entry = new LedgerEntry();
        entry.setUserId(userId);
        entry.setType(request.getType());
        entry.setAmount(request.getAmount());
        entry.setMemo(request.getMemo());
        entry.setDate(request.getDate());
        entry.setCategoryId(request.getCategoryId());

        ledgerMapper.insertLedgerEntry(entry);
    }

    public void deleteLedgerEntry(Long entryId, Long userId) {
        LedgerEntry entry = ledgerMapper.findById(entryId);
        if (entry == null || !entry.getUserId().equals(userId)) {
            throw new RuntimeException("삭제 권한 없음");
        }
        ledgerMapper.deleteLedgerEntryById(entryId);
    }

    public void updateLedgerEntry(Long id, Long userId, LedgerEntryInsertRequest request) {
        LedgerEntry entry = ledgerMapper.findById(id);
        if (entry == null || !entry.getUserId().equals(userId)) {
            throw new RuntimeException("수정 권한 없음");
        }
        entry.setType(request.getType());
        entry.setAmount(request.getAmount());
        entry.setMemo(request.getMemo());
        entry.setDate(request.getDate());
        entry.setCategoryId(request.getCategoryId());
        ledgerMapper.updateLedgerEntry(entry);
    }

    public List<LedgerReportResponse> getMonthlyReport(Long userId, String month) {
        String startDate = month + "-01";
        LocalDate end = LocalDate.parse(startDate).withDayOfMonth(LocalDate.parse(startDate).lengthOfMonth());
        return ledgerMapper.getMonthlyReport(userId, startDate, end.toString());
    }
}
