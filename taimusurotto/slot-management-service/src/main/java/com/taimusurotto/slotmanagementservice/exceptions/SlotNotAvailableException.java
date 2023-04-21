package com.taimusurotto.slotmanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Slots are not available")
public class SlotNotAvailableException extends Exception{
    public SlotNotAvailableException(String message){
        super(message);
    }
}
