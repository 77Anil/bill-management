package com.billnex.billManage.dto;

import com.billnex.billManage.entity.Role;
import com.billnex.billManage.entity.UserDetails;
import com.billnex.billManage.entity.UserStatus;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {


    private String id;

    private String name;

    private String department;

    private String email;

    private UserStatus status;

    private Role role;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

}
