package com.taimusurotto.slotmanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Interviewer Already Exists")
public class InterviewerAlreadyExists extends Exception{
    public InterviewerAlreadyExists() {
        super("Interviewer Already Exists");
    }


}
