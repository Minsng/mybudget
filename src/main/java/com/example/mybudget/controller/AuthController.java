package com.example.mybudget.controller;

import com.example.mybudget.dto.LoginRequest;
import com.example.mybudget.dto.UserSignupRequest;
import com.example.mybudget.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 예시: 이메일이 test@test.com 이고 비번이 1234이면 성공
        if ("tjdalstmd033@naver.com".equals(request.getEmail()) && "1234".equals(request.getPassword())) {
            String dummyToken = "jwt-token-example-1234"; // 여기에 진짜 JWT 생성 로직 들어감
            return ResponseEntity.ok().body(Map.of("token", dummyToken));
        } else {
            return ResponseEntity.status(401).body("이메일 또는 비밀번호가 틀렸습니다.");
        }
    }
}
