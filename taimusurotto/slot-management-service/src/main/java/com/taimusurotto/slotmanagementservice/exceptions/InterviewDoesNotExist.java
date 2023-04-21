package com.taimusurotto.slotmanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Interview Does not Exist")
public class InterviewDoesNotExist extends Exception{
    public InterviewDoesNotExist(){
        super("Interview Does Not Exist");
    }
}
