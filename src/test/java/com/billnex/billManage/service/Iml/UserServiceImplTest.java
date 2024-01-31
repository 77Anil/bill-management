package com.billnex.billManage.service.Iml;

import com.billnex.billManage.dto.UserRequestDto;
import com.billnex.billManage.dto.UserResponseDto;
import com.billnex.billManage.entity.User;
import com.billnex.billManage.exception.NotFoundException;
import com.billnex.billManage.repository.UserRepository;
import com.billnex.billManage.service.Impl.UserServiceImpl;
import com.billnex.billManage.utils.CommonDBMethods;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    CommonDBMethods commonDBMethods;


    private User createUserInstance(String mail){
        return User.builder()
                .email(mail)
                .department("CSE")
                .password("password")
                .name("Anil")
                .build();
    }

    private UserRequestDto createUserRequestDto(String mail){
        return UserRequestDto.builder()
                .email(mail)
                .department("CSE")
                .password("password")
                .name("Anil")
                .build();
    }

    private UserResponseDto createUserResponseDto(String mail){
        return UserResponseDto.builder()
                .email(mail)
                .department("CSE")
                .name("Anil")
                .build();
    }

    @Test
    public void userService_CreateUser_Return201(){

        User user = createUserInstance("test@gmail.com");

        UserRequestDto userRequestDto = createUserRequestDto("test@gmail.com");

        when(modelMapper.map(userRequestDto, User.class)).thenReturn(user);

        userServiceImpl.addUser(userRequestDto);
        verify(userRepository).save(user);
    }

    @Test
    public void userService_GetAllUsers_ReturnUsers(){
        User user1 = createUserInstance("test@gmail.com");
        User user2 = createUserInstance("testtow@gmail.com");

        List<User> users = Arrays.asList(user1, user2);

        UserResponseDto userResponseDto1 = createUserResponseDto("test@gmail.com");

        UserResponseDto userResponseDto2 = createUserResponseDto("testtow@gmail.com");

        List<UserResponseDto> userResponses = Arrays.asList(userResponseDto1, userResponseDto2);

        when(userRepository.findAll()).thenReturn(users);
        when(modelMapper.map(users.get(0), UserResponseDto.class)).thenReturn(userResponses.get(0));
        when(modelMapper.map(users.get(1), UserResponseDto.class)).thenReturn(userResponses.get(1));

        List<UserResponseDto> allUsers = userServiceImpl.getAllUsers();

        assertEquals(userResponses, allUsers);
        verify(userRepository).findAll();
    }

    @Test
    public void userService_GetUserById_ReturnUser(){

        User user = createUserInstance("testtow@gmail.com");

        UserResponseDto userResponseDto = createUserResponseDto("testtow@gmail.com");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto result = userServiceImpl.getUserById("abc");

        assertEquals(userResponseDto, result);
        verify(userRepository).findById("abc");
    }

    @Test
     void userService_GetUserById_ReturnErrorNotFound(){
        when(userRepository.findById("abc")).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userServiceImpl.getUserById("abc"));
        assertEquals("User not Found", exception.getMessage());
    }

    @Test
    public void userService_DeleteUserById_Return200(){

        User user = createUserInstance("testtow@gmail.com");

        when(commonDBMethods.findUserById(anyString())).thenReturn(user);

        userServiceImpl.deleteUserById("abc");

        verify(userRepository).delete(user);
    }

    @Test
    public void userService_UpdateUserById_Return200(){

        User user = createUserInstance("testtow@gmail.com");



        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Sunil")
                .build();

        when(commonDBMethods.findUserById(anyString())).thenReturn(user);

        userServiceImpl.updateUserById("abc", userRequestDto);

        verify(userRepository).save(user);
    }



}
