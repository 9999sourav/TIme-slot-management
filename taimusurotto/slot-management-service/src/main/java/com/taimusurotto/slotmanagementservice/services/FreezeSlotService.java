package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.FreezeTime;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.exceptions.FreezeTimeAlreadySet;
import com.taimusurotto.slotmanagementservice.exceptions.FreezeTimeDoesNotExist;
import com.taimusurotto.slotmanagementservice.repositories.FreezeTimeRepository;
import com.taimusurotto.slotmanagementservice.repositories.MasterTableRepository;
import com.taimusurotto.slotmanagementservice.repositories.SlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FreezeSlotService {

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    MasterTableRepository masterTableRepository;

    @Autowired
    FreezeTimeRepository freezeTimeRepository;
    @Autowired
    private EmailSenderService senderService;

    static int freezeTime = 7;

    public FreezeTime saveFreezeTime(FreezeTime freezeTime) throws FreezeTimeAlreadySet {
        if (freezeTimeRepository.findFreezeTimeForDate(freezeTime.getFreeze_date())!=null){
            throw new FreezeTimeAlreadySet();
        }
        else {
            return freezeTimeRepository.save(freezeTime);
        }
    }

    public FreezeTime updateFreezeTime(FreezeTime freezeTime) throws FreezeTimeDoesNotExist {
        if (freezeTimeRepository.findFreezeTimeForDate(freezeTime.getFreeze_date())!=null){
            FreezeTime freezeTime1 = freezeTimeRepository.findFreezeTimeForDate(freezeTime.getFreeze_date());
            freezeTime1.setFreeze_time(freezeTime.getFreeze_time());
            return freezeTimeRepository.save(freezeTime1);
        }
        else {
            throw new FreezeTimeDoesNotExist();
        }
    }

    public FreezeTime checkFreezeTime(FreezeTime freezeTime){
        return freezeTimeRepository.findFreezeTimeForDate(freezeTime.getFreeze_date());
    }


//    This Scheduler runs every day at 00:00 hrs
    @Scheduled(cron = "0 0 0 * * ?")
    public void setFreezeTime(){
        if (freezeTimeRepository.findFreezeTimeForDate(LocalDate.now())!=null) {
            freezeTime = freezeTimeRepository.findFreezeTimeForDate(LocalDate.now()).getFreeze_time();
            log.info("Freeze Time for today is : 0"+freezeTime+":00 hrs");
        }
        else {
            log.info("Freeze Time for today has not been slot, slots will be frozen at 07:00 hrs");
            freezeTime=7;
        }
    }

//      This Scheduler runs every hour
    @Scheduled(cron = "0 0 0/1 * * ?")
     public void checkCallSlots() throws InterruptedException, MessagingException, IOException {
        freezeSlots();
     }

    public void freezeSlots() throws InterruptedException, MessagingException, IOException {
        int hour=LocalTime.now().getHour();
        if (hour==0){
            Thread.sleep(100000);
        }
        log.info("Freezing Slots");
        log.info(String.valueOf(hour));
        if(hour==freezeTime) {
            System.out.println("Scheduled run time : "+ LocalDateTime.now());
            LocalDate freezeDate = LocalDate.now();
            List<Slot> toBeFreezedSlots = slotRepository.getSlotsByDate(freezeDate);
            List<Integer> slotIds = new ArrayList<>();
            for (Slot slot : toBeFreezedSlots){
                slotIds.add(slot.getSlot_Id());
            }
            for (int id:slotIds) {
                if (masterTableRepository.findAllBySlotId(id)!=null){
                    List<MasterTable> masterTable = masterTableRepository.findAllBySlotId(id);
                    for(MasterTable masterTable1 : masterTable) {
                        if (masterTable1.getStatus().equalsIgnoreCase("booked")) {
                            senderService.sendEmailFromTemplate3(masterTable1);
                        }
                        String status = masterTable1.getStatus();
                        status = status.concat("/F");
                        masterTable1.setStatus(status);
                        masterTableRepository.save(masterTable1);
                        log.info(String.valueOf(masterTable));
                    }
                }
            }
        }
    }

}
