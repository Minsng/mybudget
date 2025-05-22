package com.example.mybudget.controller;

import com.example.mybudget.domain.LedgerEntry;
import com.example.mybudget.dto.LedgerEntryInsertRequest;
import com.example.mybudget.security.CustomUserDetails; // 사용 중이라면
import com.example.mybudget.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    @GetMapping
    public ResponseEntity<List<LedgerEntry>> getLedger(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        return ResponseEntity.ok(ledgerService.getEntriesByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> addLedgerEntry(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @RequestBody LedgerEntryInsertRequest request) {
        Long userId = userDetails.getUser().getId();
        ledgerService.addEntry(userId, request);

        return ResponseEntity.ok("항목 추가 완료");
    }
}
