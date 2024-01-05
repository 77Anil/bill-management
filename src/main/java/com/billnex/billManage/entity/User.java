package com.billnex.billManage.entity;


import lombok.*;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    private String name;

    private String department;

    private String email;

    private UserStatus status;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;
}
