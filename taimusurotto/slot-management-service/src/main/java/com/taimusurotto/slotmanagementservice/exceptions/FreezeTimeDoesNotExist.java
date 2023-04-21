package com.taimusurotto.slotmanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Freeze Time has not been set for this date")
public class FreezeTimeDoesNotExist extends Exception{
    public FreezeTimeDoesNotExist(){
        super("Freeze Time has not been set for this date");
    }
}
