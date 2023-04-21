package com.taimusurotto.slotmanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "The credentials provided are invalid")
public class InvalidCredentials extends Exception{
    public InvalidCredentials(){
        super("The credentials provided are invalid");
    }
}
