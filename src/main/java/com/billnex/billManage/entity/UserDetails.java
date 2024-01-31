package com.billnex.billManage.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {

    private String name;

    private String department;
}
