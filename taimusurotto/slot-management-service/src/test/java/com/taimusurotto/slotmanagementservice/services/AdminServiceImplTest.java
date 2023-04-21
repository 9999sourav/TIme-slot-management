package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.Admin;
import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.exceptions.AdminAlreadyExists;
import com.taimusurotto.slotmanagementservice.exceptions.InterviewerAlreadyExists;
import com.taimusurotto.slotmanagementservice.exceptions.SlotAlreadyExists;
import com.taimusurotto.slotmanagementservice.exceptions.SlotDoesNotExist;
import com.taimusurotto.slotmanagementservice.repositories.AdminRepository;
import com.taimusurotto.slotmanagementservice.repositories.InterviewerRepository;
import com.taimusurotto.slotmanagementservice.repositories.MasterTableRepository;
import com.taimusurotto.slotmanagementservice.repositories.SlotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    AdminRepository adminRepository;

    @Mock
    SlotRepository slotRepository;

    @Mock
    InterviewerRepository interviewerRepository;

    @Mock
    MasterTableRepository masterTableRepository;

    @InjectMocks
    AdminServiceImpl adminService;


    Admin admin;
    Slot slot;
    Interviewer interviewer;
    MasterTable masterTableData;
    List<Slot> slots;
    List<Integer> slot_ids;
    List<MasterTable> masterTableSlots;



    @BeforeEach
    public void setUp(){
        admin = new Admin(1,"Mehul","Admin@1234",null);
        slot = new Slot(1, LocalDate.now(), LocalTime.now(),LocalTime.now(),null,5,0,3);
        interviewer = new Interviewer(1,"Mehul","Mahajan","Mahajan214@gmail.com",null,9682101021L);
        masterTableData = new MasterTable(1,null,null,slot,null,null,null,null,null);
        slots = new ArrayList<>();
        slots.add(slot);
        slot_ids = new ArrayList<>();
        slot_ids.add(slot.getSlot_Id());
        masterTableSlots = new ArrayList<>();
        masterTableSlots.add(masterTableData);
    }

    @AfterEach
    public void tearDown(){
        admin = null;
        slot = null;
        interviewer = null;
        masterTableData = null;
        slots = null;
        slot_ids = null;
        masterTableSlots = null;
    }

    @Test
    void addAdmin() throws AdminAlreadyExists {
        when(adminRepository.findByUsername(any())).thenReturn(null);
        when(adminRepository.save(any())).thenReturn(admin);
        assertEquals(admin,adminService.addAdmin(admin));
    }

    @Test
    void createSlot() throws SlotAlreadyExists {
        when(slotRepository.findSlot(any(),any(),any())).thenReturn(null);
        when(slotRepository.save(slot)).thenReturn(slot);
        assertEquals( slots,adminService.createSlot(slots));
    }

//    @Test
//    void deleteSlot() throws SlotDoesNotExist {
//        when(slotRepository.findById(anyInt())).thenReturn(Optional.empty());
//        when(slotRepository.deleteById(anyInt())).thenReturn()
//        assertEquals("Slots Deleted Successfully",adminService.deleteSlot(slot_ids));
//    }

    @Test
    void addInterviewer() throws InterviewerAlreadyExists {
        when(interviewerRepository.findByEmail(any())).thenReturn(null);
        when(interviewerRepository.save(interviewer)).thenReturn(interviewer);
        assertEquals(interviewer,adminService.addInterviewer(interviewer));
    }

    @Test
    void getSlotsForAParticularDay() {
        when(slotRepository.getSlotsByDate(LocalDate.now())).thenReturn(slots);
        assertEquals(slots,adminService.getSlotsForAParticularDay(LocalDate.now()));
    }

    @Test
    void findSlotsDetailsForASlotId() throws SlotDoesNotExist {
        when(masterTableRepository.findSlotDetailsForSlotId(anyInt())).thenReturn(masterTableSlots);
        assertEquals(masterTableSlots,adminService.findSlotsDetailsForASlotId(anyInt()));

    }

}