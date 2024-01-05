package com.billnex.billManage.controller;

import com.billnex.billManage.dto.UserRequestDto;
import com.billnex.billManage.dto.UserResponseDto;
import com.billnex.billManage.service.Impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody UserRequestDto userRequestDto){
        userServiceImpl.addUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String Id){

        return ResponseEntity.ok(userServiceImpl.getUserById(Id));
    }

    @PutMapping("/{Id}")
    public ResponseEntity updateUserByID(@PathVariable String Id, @RequestBody UserRequestDto userRequestDto){
        userServiceImpl.updateUserById(Id, userRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity deleteUserById(@PathVariable String Id){
        userServiceImpl.deleteUserById(Id);
        return ResponseEntity.ok().build();
    }

}
