package com.billnex.billManage.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequestDto {
    private String name;

    private String department;
}
