package com.billnex.billManage.dto;

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
public class SignInRequestDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
}
