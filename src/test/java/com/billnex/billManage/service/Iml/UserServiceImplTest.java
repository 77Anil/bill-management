package com.billnex.billManage.service.Iml;

import com.billnex.billManage.dto.UserRequestDto;
import com.billnex.billManage.dto.UserResponseDto;
import com.billnex.billManage.entity.User;
import com.billnex.billManage.exception.NotFoundException;
import com.billnex.billManage.repository.UserRepository;
import com.billnex.billManage.service.Impl.UserServiceImpl;
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



    @Test
    public void userService_CreateUser_Return201(){

        User user = User.builder()
                .email("test@gmail.com")
                .department("CSE")
                .name("Anil")
                .build();

        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Anil")
                .department("CSE")
                .email("test@gmail.com")
                .build();

        when(modelMapper.map(userRequestDto, User.class)).thenReturn(user);

        userServiceImpl.addUser(userRequestDto);
        verify(userRepository).save(user);
    }

    @Test
    public void userService_GetAllUsers_ReturnUsers(){
        User user1 = User.builder()
                .email("test@gmail.com")
                .department("CSE")
                .name("Anil")
                .build();
        User user2 = User.builder()
                .email("test@gmail.com")
                .department("CSE")
                .name("Sunil")
                .build();

        List<User> users = Arrays.asList(user1, user2);

        UserResponseDto userResponseDto1 = UserResponseDto.builder()
                .name("Anil")
                .id("abc")
                .department("CSE")
                .email("test@gmail.com")
                .build();

        UserResponseDto userResponseDto2 = UserResponseDto.builder()
                .name("Sunil")
                .id("abc")
                .department("CSE")
                .email("test@gmail.com")
                .build();

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

        User user = User.builder()
                .email("test@gmail.com")
                .id("abc")
                .department("CSE")
                .name("Anil")
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .name("Anil")
                .id("abc")
                .department("CSE")
                .email("test@gmail.com")
                .build();

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

        User user = User.builder()
                .email("test@gmail.com")
                .id("abc")
                .department("CSE")
                .name("Anil")
                .build();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        userServiceImpl.deleteUserById("abc");

        verify(userRepository).delete(user);
    }

    @Test
    public void userService_UpdateUserById_Return200(){

        User user = User.builder()
                .email("test@gmail.com")
                .department("CSE")
                .name("Anil")
                .build();



        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Sunil")
                .build();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        userServiceImpl.updateUserById("abc", userRequestDto);

        verify(userRepository).save(user);
    }



}
