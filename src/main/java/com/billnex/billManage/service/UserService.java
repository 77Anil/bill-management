package com.billnex.billManage.service;

import com.billnex.billManage.dto.UserProfileRequestDto;
import com.billnex.billManage.dto.UserRequestDto;
import com.billnex.billManage.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.naming.AuthenticationException;
import java.util.List;

public interface UserService {
    void addUser(UserRequestDto requestDto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(String userId);
    void updateUserById(String userId, UserRequestDto userRequestDto);
    void deleteUserById(String userId);
    void updateUserProfile(String userId, UserProfileRequestDto userProfileRequestDto);
    UserDetailsService userDetailsService();
}
