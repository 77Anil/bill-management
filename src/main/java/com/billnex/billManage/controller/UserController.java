package com.billnex.billManage.controller;

import com.billnex.billManage.dto.UserProfileRequestDto;
import com.billnex.billManage.dto.UserRequestDto;
import com.billnex.billManage.dto.UserResponseDto;
import com.billnex.billManage.service.Impl.UserServiceImpl;
import com.billnex.billManage.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@SecurityRequirement(name = "/v3/api-docs")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody UserRequestDto userRequestDto){
        userService.addUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String Id){

        return ResponseEntity.ok(userService.getUserById(Id));
    }

    @PutMapping("/{Id}")
    public ResponseEntity updateUserByID(@PathVariable String Id, @RequestBody UserRequestDto userRequestDto){
        userService.updateUserById(Id, userRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{Id}/profile")
    public ResponseEntity updateUserProfile(@PathVariable String Id, @RequestBody UserProfileRequestDto userProfileRequestDto){
        userService.updateUserProfile(Id, userProfileRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity deleteUserById(@PathVariable String Id){
        userService.deleteUserById(Id);
        return ResponseEntity.ok().build();
    }

}
