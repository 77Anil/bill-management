package com.billnex.billManage.service;

import com.billnex.billManage.dto.SignInRequestDto;
import com.billnex.billManage.dto.SignInResponseDto;
import com.billnex.billManage.dto.UserRequestDto;

public interface AuthService {
    void signUp(UserRequestDto userRequestDto);
    SignInResponseDto signIn(SignInRequestDto signInRequestDto);
}
