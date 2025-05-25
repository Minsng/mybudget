package com.example.mybudget.mapper;

import com.example.mybudget.domain.LedgerEntry;
import com.example.mybudget.dto.LedgerReportResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LedgerMapper {
    List<LedgerEntry> findByUserIdAndDateRange(@Param("userId") Long userId,
                                               @Param("start") String start,
                                               @Param("end") String end);
    void insertLedgerEntry(LedgerEntry ledgerEntry);
    int deleteLedgerEntryById(long id);
    LedgerEntry findById(long id);
    void updateLedgerEntry(LedgerEntry ledgerEntry);

    List<LedgerReportResponse> getMonthlyReport(@Param("userId") Long userId,
                                                @Param("startDate") String startDate,
                                                @Param("endDate") String endDate);
}
