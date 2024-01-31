package com.billnex.billManage.controller;

import com.billnex.billManage.dto.SignInRequestDto;
import com.billnex.billManage.dto.UserRequestDto;
import com.billnex.billManage.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("signin")
    public ResponseEntity signIn(@RequestBody SignInRequestDto signInRequestDto){
        return ResponseEntity.ok(authService.signIn(signInRequestDto));
    }

    @PostMapping("signup")
    public ResponseEntity signUp(@RequestBody UserRequestDto userRequestDto){
        authService.signUp(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
