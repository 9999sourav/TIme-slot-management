package com.taimusurotto.slotmanagementservice.controller;

import com.taimusurotto.slotmanagementservice.domain.Admin;
import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.exceptions.*;
import com.taimusurotto.slotmanagementservice.services.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    AdminServiceImpl adminService;

    @PostMapping("/addAdmin")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin) throws AdminAlreadyExists {
        try {
            return new ResponseEntity<>(adminService.addAdmin(admin), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/addInterviewer")
    public ResponseEntity<?> addInterviewer(@RequestBody Interviewer interviewer) throws InterviewerAlreadyExists {
        try {
            return new ResponseEntity<>(adminService.addInterviewer(interviewer), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deleteInterviewer/{id}")
    public ResponseEntity<?> deleteInterviewer(@PathVariable int id) throws InterviewerDoesNotExist {
        try {
            return new ResponseEntity<>(adminService.deleteInterviewer(id), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/createSlots")
    public ResponseEntity<?> createSlots(@RequestBody List<Slot> slots) throws SlotAlreadyExists {
        try {
            return new ResponseEntity<>(adminService.createSlot(slots), HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/getAllInterviewers")
    public ResponseEntity<?> getAllInterviewers() {
        return new ResponseEntity<>(adminService.getAllInterviewers(), HttpStatus.OK);
    }

    @GetMapping("/{slot_date}")
    public ResponseEntity<?> getSlots(@PathVariable String slot_date) {
        LocalDate localDate = LocalDate.parse(slot_date);
        return new ResponseEntity(adminService.getSlotsForAParticularDay(localDate), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{data_Id}")
    public ResponseEntity<?> deleteSlot(@PathVariable int data_Id) {
        log.info("inside delete slots data_id =" + data_Id);
        try {
            return new ResponseEntity(adminService.deleteSlotById(data_Id), HttpStatus.OK);
        } catch (SlotDoesNotExist e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("getAllSlots")
    public ResponseEntity<?> getAllSlots() {
        return new ResponseEntity<>(adminService.getAllSlots(), HttpStatus.OK);
    }

    @GetMapping("/getAllAdminDetails")
    public ResponseEntity<?> getAllDetails() {
        return new ResponseEntity<>(adminService.getAllAdminDetails(), HttpStatus.OK);
    }


    @DeleteMapping("/deleteAdmin/{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable int adminId) {
        return new ResponseEntity<>(adminService.DeleteAdminById(adminId), HttpStatus.OK);
    }

    @DeleteMapping("/deleteSlot/{slot_id}")
    public ResponseEntity<?> deleteSlotFromSlotRepo(@PathVariable int slot_id) throws SlotDoesNotExist {
        adminService.deleteSlotFromSlotRepos(slot_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getSlots/{date}")
    public ResponseEntity<?> getAllSlotsFromSlotRepoByDate(@PathVariable String date){
        LocalDate localDate = LocalDate.parse(date);
        return new ResponseEntity<>(adminService.getAllSlotsFromSlotRepo(localDate),HttpStatus.OK);
    }

    @DeleteMapping("cancelSlot/{data_id}")
    public ResponseEntity<?> cancelInterview(@PathVariable int data_id){
        try{
            return new ResponseEntity<>(adminService.cancelInterview(data_id),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }


}

