package com.taimusurotto.slotmanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Admin already registered")
public class AdminAlreadyExists extends Exception{
    public AdminAlreadyExists(){
        super("Admin already registered");
    }
}
