package com.billnex.billManage.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
