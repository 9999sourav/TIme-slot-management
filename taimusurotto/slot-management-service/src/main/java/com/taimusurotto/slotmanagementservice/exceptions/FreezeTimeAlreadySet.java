package com.taimusurotto.slotmanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Freeze Time has already been set for this date")
public class FreezeTimeAlreadySet extends Exception{
    public FreezeTimeAlreadySet(){
        super("Freeze Time has already been set for this date");
    }
}
