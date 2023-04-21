package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.Admin;
import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.exceptions.*;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {
    public Admin addAdmin(Admin admin) throws AdminAlreadyExists;
    public List<Slot> createSlot(List<Slot> slots) throws SlotAlreadyExists;
    public List<Integer> deleteSlot(List<Integer> ids) throws SlotDoesNotExist;
    public Interviewer addInterviewer(Interviewer interviewer) throws InterviewerAlreadyExists;
    public String deleteInterviewer(int id) throws InterviewerDoesNotExist;
    List<Interviewer> getAllInterviewers();
    List<Slot> getSlotsForAParticularDay(LocalDate date);
    boolean deleteSlotById(int data_id) throws SlotDoesNotExist;
    List<MasterTable> getAllSlots();

    List<Admin> getAllAdminDetails();
    boolean DeleteAdminById(int adminId);
    List<Slot> getAllSlotsDetails();
    void deleteSlotFromSlotRepos(int slot_id) throws SlotDoesNotExist;
    List<Slot> getAllSlotsFromSlotRepo(LocalDate date);
    boolean cancelInterview(int data_id) throws InterviewDoesNotExist;

}
