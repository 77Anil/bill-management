package com.billnex.billManage.service.Impl;

import com.billnex.billManage.dto.UserProfileRequestDto;
import com.billnex.billManage.dto.UserRequestDto;
import com.billnex.billManage.dto.UserResponseDto;
import com.billnex.billManage.entity.User;
import com.billnex.billManage.entity.UserDetails;
import com.billnex.billManage.entity.UserStatus;
import com.billnex.billManage.exception.NotFoundException;
import com.billnex.billManage.repository.UserRepository;
import com.billnex.billManage.service.UserService;
import com.billnex.billManage.utils.CommonDBMethods;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonDBMethods commonDBMethods;

    @Override
    public void addUser(UserRequestDto requestDto) {
        Optional<User> existingUser = userRepository.findByEmail(requestDto.getEmail());
        if(existingUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        User user = modelMapper.map(requestDto, User.class);
        user.setCreatedOn(LocalDateTime.now());
        user.setModifiedOn(LocalDateTime.now());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDto = users.stream()
                .map(user -> modelMapper
                        .map(user, UserResponseDto.class))
                .collect(Collectors.toList());
        return userResponseDto;
    }

    @Override
    public UserResponseDto getUserById(String userId){
       User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not Found"));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public void updateUserById(String userId, UserRequestDto userRequestDto){
        User user = commonDBMethods.findUserById(userId);
        modelMapper.map(userRequestDto, user);
        user.setModifiedOn(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(String userId){
        User user = commonDBMethods.findUserById(userId);
        userRepository.delete(user);
    }

    @Override
    public void updateUserProfile(String userId, UserProfileRequestDto userProfileRequestDto) {
        UserDetails userDetails = modelMapper.map(userProfileRequestDto, UserDetails.class);
        User user = commonDBMethods.findUserById(userId);
//        user.setUserDetails(userDetails);
        userRepository.save(user);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return (email) -> commonDBMethods.findUserByEmail(email);
    }




}
