package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.Admin;
import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.exceptions.*;
import com.taimusurotto.slotmanagementservice.repositories.AdminRepository;
import com.taimusurotto.slotmanagementservice.repositories.InterviewerRepository;
import com.taimusurotto.slotmanagementservice.repositories.MasterTableRepository;
import com.taimusurotto.slotmanagementservice.repositories.SlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    EmailSenderService senderService;

    @Autowired
    InterviewerRepository interviewerRepository;

    @Autowired
    MasterTableRepository masterTableRepository;

    @Override
    public Admin addAdmin(Admin admin) throws AdminAlreadyExists {
        log.info("Adding Admin : "+admin);
        if (adminRepository.findByUsername(admin.getUserName())!=null)
        {
            log.error("Admin already exists");
            throw new AdminAlreadyExists();
        }
        admin.setPassword("Admin@1234");
        try{
            senderService.sendEmailToNewAdmin(admin);
        }
        catch (Exception e){
            log.warn("Error=>"+e.getMessage());
            //logic to store failed emails
        }
        return adminRepository.save(admin);
    }

    @Override
    public List<Slot> createSlot(List<Slot> slots) throws SlotAlreadyExists {
        log.info("Creating Slots");
        for (Slot s : slots){
            log.info("Adding Slot : "+s);
            if(slotRepository.findSlot(s.getSlot_date(), s.getSlot_start_Time(),s.getSlot_end_Time())!=null){
                log.error("Slot Already Exists");
                throw new SlotAlreadyExists();
            }
            s.setBookings(0);
            s.setLimit(10);
            slotRepository.save(s);
        }
        return slots;
    }

    @Override
    public List<Integer> deleteSlot(List<Integer> ids) throws SlotDoesNotExist {
        log.info("Deleting Slots");
        for (int id : ids ){
            log.info("Deleting slot having id : "+ id);
            if(!slotRepository.findById(id).isPresent()){
                log.error("Slot Does Not Exists");
                throw new SlotDoesNotExist();
            }
            slotRepository.deleteById(id);
        }
        return ids;
    }

    @Override
    public Interviewer addInterviewer(Interviewer interviewer) throws InterviewerAlreadyExists {
        log.info("Adding Interviewer : "+interviewer);
        if(interviewerRepository.findByEmail(interviewer.getEmail())!=null){
            log.error("Interviewer Already Exists");
            throw new InterviewerAlreadyExists();
        }
        else {
            interviewer.setPassword("Interviewer@1234");
            try{
                senderService.sendEmailFromTemplate4(interviewer);
            }
            catch (Exception e){
                log.warn("Error=>"+e.getMessage());
                //logic to store failed emails
            }
            return interviewerRepository.save(interviewer);
        }
    }

    @Override
    public String deleteInterviewer(int id) throws InterviewerDoesNotExist {
        log.info("Deleting Interviewer having id : "+id);
        if(interviewerRepository.findById(id).isEmpty()){
            log.error("Interviewer Does Not Exist");
            throw new InterviewerDoesNotExist();
        }
        List<MasterTable> masterTablesList = masterTableRepository.getAllSlotsForInterviewer(id);
        List<MasterTable> slotsToBeCancelled = new ArrayList<>();
        for(MasterTable slot : masterTablesList){
            if(slot.getStatus().equalsIgnoreCase("booked")){
                slotsToBeCancelled.add(slot);
            }
            masterTableRepository.delete(slot);
        }
        interviewerRepository.deleteById(id);
        return "Interviewer Deleted Successfully";
    }

    @Override
    public List<Interviewer> getAllInterviewers() {
        return interviewerRepository.findAll();
    }


    public List<Slot> getSlotsForAParticularDay(LocalDate date){
        List<Slot> slots=slotRepository.getSlotsByDate(date);
        log.info(slots.toString());
        if(slots.isEmpty()){
            log.error("No Slots are available");
        }
        return slots;
    }

    @Override
    public boolean deleteSlotById(int data_id) throws SlotDoesNotExist {
        if(masterTableRepository.findById(data_id).isEmpty()){
            throw new SlotDoesNotExist();
        }
        masterTableRepository.deleteById(data_id);
        return true;
    }

    @Override
    public List<MasterTable> getAllSlots() {
        log.info("Returning All Slots");
        return masterTableRepository.findAll();
    }

    @Override
    public List<Admin> getAllAdminDetails() {
        return adminRepository.findAll();
    }


    @Override
    public boolean DeleteAdminById(int adminId) {

        adminRepository.deleteById(adminId);
        return true;
    }


    @Override
    public List<Slot> getAllSlotsDetails() {
        return slotRepository.findAll();
    }

    @Override
    public void deleteSlotFromSlotRepos(int slot_id) throws SlotDoesNotExist {
        if (slotRepository.findById(slot_id).isEmpty()){
            throw new SlotDoesNotExist();
        }
        if(!masterTableRepository.findAllBySlotId(slot_id).isEmpty()){
            for(MasterTable masterTable: masterTableRepository.findAllBySlotId(slot_id)){
                masterTableRepository.delete(masterTable);
            }
        }
        slotRepository.deleteById(slot_id);
    }

    @Override
    public List<Slot> getAllSlotsFromSlotRepo(LocalDate date){
        log.info("returning--->"+slotRepository.getSlotsByDate(date));
        return slotRepository.getSlotsByDate(date);
    }

    @Override
    public boolean cancelInterview(int data_id) throws InterviewDoesNotExist {
        if (masterTableRepository.findById(data_id).isEmpty()){
            throw new InterviewDoesNotExist();
        }
        else {
            MasterTable interview = masterTableRepository.findById(data_id).get();
            interview.setStatus("Cancelled");
            interview.setCanceled_by("Admin");
            masterTableRepository.save(interview);
            return true;
        }
    }

    @Scheduled(cron = "0 0,30 * * * ?")
    public void checkCompletedTimeSlots() {
        log.info("Checking For Slots that have passed / have been completed");
        List<Slot> slots = slotRepository.getSlotsByDate(LocalDate.now());
        List<MasterTable> masterTableList = new ArrayList<>();
        log.info("Checking Slot Time");
        for (Slot s : slots){
            if (s.getSlot_end_Time().getHour()==LocalTime.now().getHour() && s.getSlot_end_Time().getMinute()==LocalTime.now().getMinute()){
                masterTableList.addAll(masterTableRepository.findAllBySlotId(s.getSlot_Id()));
            }
        }
        log.info("Changing Slot Status to completed for Slots : ");
        for (MasterTable m : masterTableList){
            log.info(String.valueOf(m.getSlot_id()));
            m.setStatus("Completed");
            masterTableRepository.save(m);
        }
    }




}
