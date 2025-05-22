package com.example.mybudget.service;

import com.example.mybudget.domain.User;
import com.example.mybudget.dto.UserSignupRequest;
import com.example.mybudget.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public void signup(UserSignupRequest dto) {
        // 이메일 없을 시
        if (userMapper.findUserByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getNickname());

        userMapper.insertUser(user);
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userMapper.findUserByEmailAndPassword(email, password);
    }
}
