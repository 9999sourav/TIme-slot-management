package com.taimusurotto.slotmanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No user with given email was found")
public class UserNotFound extends Exception{
    public UserNotFound(){
        super("No user with given email was found");
    }
}
