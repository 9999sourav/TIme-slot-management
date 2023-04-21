package com.taimusurotto.slotmanagementservice.controller;

import com.taimusurotto.slotmanagementservice.SlotManagementServiceApplication;
import com.taimusurotto.slotmanagementservice.domain.Interviewee;
import com.taimusurotto.slotmanagementservice.exceptions.IntervieweeAlreadyExistsException;
import com.taimusurotto.slotmanagementservice.exceptions.IntervieweeNotFound;
import com.taimusurotto.slotmanagementservice.exceptions.SlotDoesNotExist;
import com.taimusurotto.slotmanagementservice.exceptions.SlotNotAvailableException;
import com.taimusurotto.slotmanagementservice.services.IntervieweeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/interviewee")
public class IntervieweeController {
    IntervieweeServiceImpl intervieweeService;
    ResponseEntity responseEntity;
    Logger logger = LoggerFactory.getLogger(SlotManagementServiceApplication.class);

    @Autowired
    public IntervieweeController(IntervieweeServiceImpl intervieweeService) {
        this.intervieweeService = intervieweeService;
    }

    @PostMapping("/book/{slotId}")
    public ResponseEntity<?> bookASlot(@PathVariable int slotId, @RequestBody int intervieweeId) {
        try {
            responseEntity = new ResponseEntity<>(intervieweeService.bookASlot(intervieweeId, slotId), HttpStatus.OK);
        } catch (SlotNotAvailableException e) {
            logger.error(e.getMessage());
        } catch (IntervieweeAlreadyExistsException e) {
            logger.error(e.getMessage());
        } catch (IntervieweeNotFound e) {
            logger.error(e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseEntity;
    }

    @PostMapping ("/delete/{slotId}/{intervieweeId}")
    public ResponseEntity<?> deleteByInterviewee(@PathVariable int slotId, @PathVariable int intervieweeId, @RequestBody String reason) {
        try {
            responseEntity = new ResponseEntity<>(intervieweeService.cancelASlot(intervieweeId, slotId, reason), HttpStatus.OK);
        } catch (IntervieweeNotFound e) {
            e.getMessage();
        } catch (SlotDoesNotExist e) {
            e.getMessage();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseEntity;
    }
    @PostMapping
    public ResponseEntity<?> addTheInterviewee(@RequestBody Interviewee interviewee){
        responseEntity=new ResponseEntity<>(intervieweeService.addInterviewee(interviewee),HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/allSlots/{date}")
    public  ResponseEntity<?> getAvailableSlotsByDate(@PathVariable String date){
        return  new ResponseEntity<>(intervieweeService.availableSlotsByDate(date), HttpStatus.OK);
    }

    @GetMapping("/boookedslot/{intervieweeId}")
    public  ResponseEntity<?> getBookedSlotByIntervieweeId(@PathVariable int intervieweeId){
        return new ResponseEntity<>(intervieweeService.getBookedSlot(intervieweeId),HttpStatus.OK);
    }

    @GetMapping("/allSlots")
    public ResponseEntity<?> getAllTheAvailableSlots() {
        try {
            responseEntity = new ResponseEntity<>(intervieweeService.availableSlots(), HttpStatus.OK);
        } catch (SlotNotAvailableException e) {
            logger.error(e.getMessage());
        }
        return responseEntity;
    }

}
