package com.billnex.billManage.utils;

import com.billnex.billManage.entity.User;
import com.billnex.billManage.exception.NotFoundException;
import com.billnex.billManage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonDBMethods {

    @Autowired
    UserRepository userRepository;

    public User findUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }

}
