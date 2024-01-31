package com.billnex.billManage.dto;

import com.billnex.billManage.entity.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotEmpty
    private String name;

    @NotEmpty
    private String department;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    private Role role;



    @JsonCreator
    public UserRequestDto(@JsonProperty("role") String role){
        this.role = (role != null) ? Role.valueOf(role.toUpperCase()) : Role.USER;
    }




}
