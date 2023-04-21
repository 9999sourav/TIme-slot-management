package com.taimusurotto.slotmanagementservice.controller;

import com.taimusurotto.slotmanagementservice.exceptions.InterviewerDoesNotExist;
import com.taimusurotto.slotmanagementservice.exceptions.MaxInterviewersReached;
import com.taimusurotto.slotmanagementservice.exceptions.SlotDoesNotExist;
import com.taimusurotto.slotmanagementservice.services.InterviewerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/interviewer")
@Slf4j
public class InterviewerController {

    @Autowired
    private InterviewerService interviewerService;
    ResponseEntity responseEntity;

    @PostMapping("/addslots/{interviewer_id}")
    public ResponseEntity<?> addSlot(@RequestBody List<Integer> slot_id, @PathVariable int interviewer_id){

        try {
            responseEntity= new ResponseEntity<>(interviewerService.addSlotAvailability(slot_id, interviewer_id), HttpStatus.OK);
        } catch (SlotDoesNotExist | MaxInterviewersReached e) {
            log.error("slot Not Found");
            e.getMessage();
        }
        return responseEntity;
    }

    @PostMapping("cancelSlot/{interviewerId}/{slot_id}")
    public ResponseEntity<?> cancelOpenSlot(@PathVariable int interviewerId, @PathVariable int slot_id, @RequestBody String reason) throws InterviewerDoesNotExist, SlotDoesNotExist, MessagingException, IOException {
        try{
           return new ResponseEntity(interviewerService.cancelSlot(slot_id,interviewerId,reason),HttpStatus.OK);
        }
        catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/addremarks/{dataId}")
    public ResponseEntity<?> addRemarks(@RequestBody String remarks,@PathVariable int dataId)
    {
        return new ResponseEntity<>(interviewerService.markAttendance(dataId, remarks),HttpStatus.OK);
    }
    
    @GetMapping("/allSlots/{interviewer_id}")
    public ResponseEntity<?> getAllSlots(@PathVariable int interviewer_id){
            return new ResponseEntity<>(interviewerService.getAllSlotsByInterviewer(interviewer_id), HttpStatus.OK);
    }

    @GetMapping("/getSlots/{date}/{interviewerId}")
    public ResponseEntity<?> getSlotsByDate(@PathVariable String date,@PathVariable int interviewerId){
        LocalDate localDate = LocalDate.parse(date);
        return new ResponseEntity<>(interviewerService.getSlotsByDate(localDate, interviewerId),HttpStatus.OK);
    }

}
