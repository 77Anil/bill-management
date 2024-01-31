package com.billnex.billManage.controller;

import com.billnex.billManage.dto.UserRequestDto;
import com.billnex.billManage.dto.UserResponseDto;
import com.billnex.billManage.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    UserService userservice;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void userController_AddUser_Return201() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Anil")
                .department("CSE")
                .email("test@gmail.com")
                .password("password")
                .build();


        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/users")
                .content(objectMapper.writeValueAsString(userRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(userservice, times(1)).addUser(any(UserRequestDto.class));

    }

    @Test
    void userController_GetUsers_ReturnUsers() throws Exception {
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

        when(userservice.getAllUsers()).thenReturn(userResponses);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].name").value("Anil"));

        verify(userservice, times(1)).getAllUsers();

    }

    @Test
    void userController_GetUserByID_ReturnUser() throws Exception {
        UserResponseDto userResponse = UserResponseDto.builder()
                .name("Anil")
                .id("abc")
                .department("CSE")
                .email("test@gmail.com")
                .build();

        when(userservice.getUserById(anyString())).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc"))
                .andExpect(jsonPath("$.name").value("Anil"));

        verify(userservice, times(1)).getUserById(anyString());

    }

    @Test
    void userController_UpdateUserById_Return200() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Sunil")
                .department("ECE")
                .build();


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/users/abc")
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userservice, times(1)).updateUserById(anyString(), any(UserRequestDto.class));

    }

    @Test
    void userController_DeleteUserById_Return200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/users/abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userservice, times(1)).deleteUserById(anyString());

    }

}
