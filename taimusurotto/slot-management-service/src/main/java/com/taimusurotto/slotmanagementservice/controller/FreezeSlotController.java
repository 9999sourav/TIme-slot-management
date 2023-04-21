package com.taimusurotto.slotmanagementservice.controller;

import com.taimusurotto.slotmanagementservice.domain.FreezeTime;
import com.taimusurotto.slotmanagementservice.exceptions.FreezeTimeAlreadySet;
import com.taimusurotto.slotmanagementservice.services.FreezeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/freeze")
public class FreezeSlotController {

    @Autowired
    FreezeSlotService freezeSlotService;

    @PostMapping("/freezeSlots")
    public ResponseEntity<?> freezeSlots(@RequestBody FreezeTime freezeTime){
        try{
            return new ResponseEntity<>(freezeSlotService.saveFreezeTime(freezeTime), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/updateFreezeSlots")
    public ResponseEntity<?> updateFreezeSlots(@RequestBody FreezeTime freezeTime){
        try{
            return new ResponseEntity<>(freezeSlotService.updateFreezeTime(freezeTime), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

}
