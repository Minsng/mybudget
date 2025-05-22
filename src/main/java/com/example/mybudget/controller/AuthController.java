package com.example.mybudget.controller;

import com.example.mybudget.domain.User;
import com.example.mybudget.dto.LoginRequest;
import com.example.mybudget.dto.UserSignupRequest;
import com.example.mybudget.security.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.findUserByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).body("이메일 또는 비밀번호가 틀렸습니다.");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", user.getEmail(),
                "nickname", user.getNickname()
        ));
    }

}
