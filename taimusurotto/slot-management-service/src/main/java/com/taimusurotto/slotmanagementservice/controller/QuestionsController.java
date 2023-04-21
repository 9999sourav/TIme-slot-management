package com.taimusurotto.slotmanagementservice.controller;

import com.taimusurotto.slotmanagementservice.services.QuestionsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@Slf4j
@RestController
@RequestMapping("/questions")

public class QuestionsController {
    QuestionsServiceImpl questionsService;
    @Autowired
    public QuestionsController(QuestionsServiceImpl questionsService) {
        this.questionsService = questionsService;
    }
    @GetMapping("/count/{slot_id}/{slot_date}")
    public ResponseEntity<?> countCandidatesGivingIntervieweeForSlotDate(@PathVariable int slot_id, @PathVariable String slot_date) {
        LocalDate localDate = LocalDate.parse(slot_date);
        return new ResponseEntity(questionsService.CountCandidatesInterviewedForSlotId(slot_id, localDate), HttpStatus.OK);
    }
    @GetMapping("/name/{slot_id}/{slot_date}")
    public ResponseEntity<?> NameOfCandidatesGivingIntervieweeForSlotDate(@PathVariable int slot_id, @PathVariable String slot_date) {
        LocalDate localDate = LocalDate.parse(slot_date);
        return new ResponseEntity(questionsService.nameOfIntervieweesForSlotId(slot_id, localDate), HttpStatus.OK);
    }
    @GetMapping("/name/{slot_date}")
    public ResponseEntity<?> totalCandidatesGivingIntervieweeForSlotDate(@PathVariable String slot_date) {
        LocalDate localDate = LocalDate.parse(slot_date);
        return new ResponseEntity(questionsService.totalCandidatesThatCanBEInterviewed(localDate), HttpStatus.OK);
    }
    @GetMapping("/count/{slot_date}")
    public ResponseEntity<?> totalSlotsForSlotDate(@PathVariable String slot_date){
        LocalDate localDate=LocalDate.parse(slot_date);
        return new ResponseEntity(questionsService.totalNumberOfSlotsForSlotDate(localDate), HttpStatus.OK);
    }
    @GetMapping("/interviewer/{slot_date}")
    public ResponseEntity<?> totalInterviewerAvailable(@PathVariable String slot_date){
        LocalDate localDate=LocalDate.parse(slot_date);
        return new ResponseEntity(questionsService.totalNumberOfInterviewerAvailable(localDate), HttpStatus.OK);
    }
      @GetMapping("/getUtilization/{date}")
        public ResponseEntity<?> getSlotUtilization (@PathVariable String date){
            LocalDate localDate = LocalDate.parse(date);
            return new ResponseEntity<>(questionsService.slotUtilization(localDate), HttpStatus.OK);
        }
        @GetMapping("/getUtilizationBySlot/{slot_id}")
        public ResponseEntity<?> getSingleSlotStats ( @PathVariable int slot_id){

            return new ResponseEntity<>(questionsService.singleSlotStats(slot_id), HttpStatus.OK);
        }
    @GetMapping("/cancelledSlots/{date}")
    public ResponseEntity<?> getCancelledSlots (@PathVariable String date){
        LocalDate localDate = LocalDate.parse(date);
        log.info("returning"+questionsService.totalCancelledSlotsForADay(localDate));
        return new ResponseEntity<>(questionsService.totalCancelledSlotsForADay(localDate), HttpStatus.OK);
    }
}