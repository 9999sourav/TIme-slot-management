package com.taimusurotto.slotmanagementservice.services;


import com.taimusurotto.slotmanagementservice.domain.MasterTable;

import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.exceptions.InterviewerDoesNotExist;
import com.taimusurotto.slotmanagementservice.exceptions.MaxInterviewersReached;
import com.taimusurotto.slotmanagementservice.exceptions.SlotDoesNotExist;
import lombok.extern.slf4j.Slf4j;

import com.taimusurotto.slotmanagementservice.repositories.InterviewerRepository;
import com.taimusurotto.slotmanagementservice.repositories.MasterTableRepository;
import com.taimusurotto.slotmanagementservice.repositories.SlotRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class InterviewerServiceImpl implements InterviewerService {
    @Autowired
    MasterTableRepository masterTableRepository;
    @Autowired
    SlotRepository slotRepository;
    @Autowired
    InterviewerRepository interviewerRepository;

    @Autowired
    EmailSenderService senderService;

    @Override

    public boolean addSlotAvailability(List<Integer> slot_Id, int interviewerId) throws SlotDoesNotExist, MaxInterviewersReached {

        for (int slot : slot_Id) {
            MasterTable masterTable = new MasterTable();
            if(slotRepository.findSlotById(slot)==null)
            {
                log.error("Slot does not exists in database");
                throw new SlotDoesNotExist();
            }
            if (slotRepository.findById(slot).get().getLimit()==slotRepository.findById(slot).get().getNo_of_available_interviewers()){
                log.error("Already has maximum interviewers allowed");
                throw new MaxInterviewersReached();
            }
            Slot slot1 = slotRepository.findSlotById(slot);
            slot1.setNo_of_available_interviewers(slot1.getNo_of_available_interviewers()+1);
            log.info(String.valueOf(slot));
            masterTable.setSlot_id(slotRepository.findById(slot).get());
            masterTable.setInterviewer(interviewerRepository.findById(interviewerId).get());
            masterTable.setStatus("Open");
            log.info(String.valueOf(masterTable));
            masterTableRepository.save(masterTable);
        }
        return true;
    }

    @Override
    public boolean markAttendance(int dataId, String remarks) {
        MasterTable slot = masterTableRepository.findById(dataId).get();
        log.info("Marking "+remarks+" for slot : "+slot.getSlot_id());
        slot.setStatus("Completed");
        remarks = remarks.substring(1, remarks.length()-1);
        slot.setRemarks(remarks);
        masterTableRepository.save(slot);
        return true;
    }

    @Override
    public List<MasterTable> getAllSlotsByInterviewer(int interviewerId) {
        log.info("Getting Slots for Interviewer Id : "+interviewerId);
        List<MasterTable> allSlots = masterTableRepository.getAllSlotsForInterviewer(interviewerId);
        return allSlots;
    }

    @Override
    public List<Slot> getSlotsByDate(LocalDate date,int interviewerId) {
        log.info("Fetching Slots for Date : "+date);
        List<Slot> slots = slotRepository.getSlotsByDate(date);
        log.info(String.valueOf(slots));
        int index = 0;
        List<Integer> count = new ArrayList<>();
        log.info("Checking and Removing slots that interviewer has already taken up");
        if(!slots.isEmpty()) {
            for (Slot slot : slots) {
                if (!masterTableRepository.findAllBySlotForInterviewer(slot.getSlot_Id(), interviewerId).isEmpty()) {
                    count.add(index);
                    log.info(String.valueOf(index));
                }
                index++;
            }
        }
        log.info(String.valueOf(count));
        log.info(String.valueOf(slots));
        int deleteIndex=0;
        if (!count.isEmpty()){
            for(int i : count){
                int a = i-deleteIndex;
                slots.remove(a);
                deleteIndex++;
            }
        }
        log.info("Removing Slots that have reached the limit");
        int c = 0;
        List<Integer> indexes = new ArrayList<>();
        if (!slots.isEmpty()){
            for (Slot slot : slots){
                if (slotRepository.findById(slot.getSlot_Id()).get().getLimit()==slotRepository.findById(slot.getSlot_Id()).get().getNo_of_available_interviewers()){
                    indexes.add(c);
                }
                c++;
            }
        }
        int removalIndex=0;
        if (!indexes.isEmpty()){
            log.info("==================");
            for(int i : indexes){
                log.info(String.valueOf(i));
                log.info(String.valueOf(removalIndex));
                int a = i-removalIndex;
                slots.remove(a);
                removalIndex++;
            }
        }
        return slots;
    }

    @Override
    public MasterTable cancelSlot(int slot_id, int interviewer_id, String reason) throws InterviewerDoesNotExist, SlotDoesNotExist, MessagingException, IOException {
        log.info("Cancelling slot for interviewer : "+interviewer_id+ " and slot : "+slot_id);
        if(interviewerRepository.findById(interviewer_id).isEmpty())
        {
            log.error("interviewer_Id is not there in database");
            throw new InterviewerDoesNotExist();
        }
        MasterTable slot = masterTableRepository.getSlot(slot_id,interviewer_id);
        if (slot == null) {
            throw new SlotDoesNotExist();
        }
        else {
            slot.setStatus("Cancelled");
            reason = reason.substring(1, reason.length()-1);
            slot.setRemarks(reason);
            slot.setCanceled_by("Interviewer");
            log.info("bookedSlot"+slot);
            log.info("Cancelling slot : "+String.valueOf(slot));
            masterTableRepository.save(slot);
            if(masterTableRepository.findOpenSlotBySlotId(slot_id)!=null){
                MasterTable newSlot = masterTableRepository.findOpenSlotBySlotId(slot_id).get(0);
                newSlot.setInterviewee(slot.getInterviewee());
                newSlot.setLink(slot.getLink());
                newSlot.setPassword(slot.getPassword());
                newSlot.setStatus("Booked");
                masterTableRepository.save(newSlot);
            }
            else{
                senderService.sendEmailFromTemplate2(slot,"Slot Cancelled");
            }
        }
        return slot;
    }

}
