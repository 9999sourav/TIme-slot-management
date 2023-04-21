package com.taimusurotto.slotmanagementservice.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Interviewee Already Exists ")
public class IntervieweeAlreadyExistsException extends Exception{
    public IntervieweeAlreadyExistsException(String message){
        super(message);
    }
}
