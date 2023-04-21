package com.taimusurotto.slotmanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "The maximum allowed interviewers are already booked for this time slot")
public class MaxInterviewersReached extends Exception {
}
