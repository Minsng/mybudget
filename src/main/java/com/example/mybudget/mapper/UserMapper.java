package com.example.mybudget.mapper;

import com.example.mybudget.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findUserByEmail(@Param("email") String email);
    User findUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);
    void insertUser(User user);
}
