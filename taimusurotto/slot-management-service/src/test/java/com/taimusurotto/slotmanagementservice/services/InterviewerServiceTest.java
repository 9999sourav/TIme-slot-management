package com.taimusurotto.slotmanagementservice.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.taimusurotto.slotmanagementservice.domain.*;
import com.taimusurotto.slotmanagementservice.exceptions.InterviewerDoesNotExist;
import com.taimusurotto.slotmanagementservice.exceptions.MaxInterviewersReached;
import com.taimusurotto.slotmanagementservice.exceptions.SlotDoesNotExist;
import com.taimusurotto.slotmanagementservice.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class InterviewerServiceTest {
    @Mock
    AdminRepository adminRepository;

    @Mock
    SlotRepository slotRepository;

    @Mock
    InterviewerRepository interviewerRepository;
    @Mock
    IntervieweeRepository intervieweeRepository;

    @Mock
    MasterTableRepository masterTableRepository;

    @InjectMocks
    InterviewerServiceImpl interviewerService;

    Admin admin;
    Slot slot;
    Interviewer interviewer;
    Interviewee interviewee;
    MasterTable masterTableData;
    List<Slot> slots;
    List<Integer> slot_ids;
    List<MasterTable> masterTableSlots;

    @BeforeEach
    public void setUp(){
        slot = new Slot(1, LocalDate.now(), LocalTime.now(),LocalTime.now(),null,5,0,2);
       slotRepository.save(slot);
        interviewer = new Interviewer(1,"Mehul","Mahajan","Mahajan214@gmail.com",null,9682101021L);
        interviewerRepository.save(interviewer);
        interviewee=new Interviewee(1,"sourav","mohanty","sourav@gmail.com",105235);
        intervieweeRepository.save(interviewee);
        masterTableData= new MasterTable();
        masterTableData.setData_Id(1);
        masterTableData.setInterviewer(interviewer);
        masterTableData.setSlot_id(slot);
        masterTableData.setStatus("Booked");
        masterTableData.setInterviewee(interviewee);
        slot_ids = new ArrayList<>();
        slot_ids.add(slot.getSlot_Id());
        masterTableSlots=new ArrayList<>();
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
    void addSlotAvailability() throws SlotDoesNotExist, MaxInterviewersReached {
        when(slotRepository.findSlotById(anyInt())).thenReturn(slot);
        when(slotRepository.findById(anyInt())).thenReturn(Optional.of(slot));
        when(interviewerRepository.findById(anyInt())).thenReturn(Optional.of(interviewer));
        when(masterTableRepository.save(any())).thenReturn(masterTableData);

        boolean status= interviewerService.addSlotAvailability(slot_ids,interviewer.getInterviewerID());
        assertTrue(status);
    }
    @Test
    void cancelInterview() throws InterviewerDoesNotExist, MessagingException, SlotDoesNotExist, IOException {
        when(interviewerRepository.findById(anyInt())).thenReturn(Optional.of(interviewer));
        when(masterTableRepository.getSlot(anyInt(),anyInt())).thenReturn(masterTableData);
        when(masterTableRepository.findOpenSlotBySlotId(1)).thenReturn(masterTableSlots);


        when(masterTableRepository.save(masterTableData)).thenReturn(masterTableData);
        System.out.println(masterTableData);
        assertEquals(interviewerService.cancelSlot(slot.getSlot_Id(),
                interviewer.getInterviewerID(), "cancelling"),masterTableData);


    }
    @Test
    void markAttendance(){
        when(masterTableRepository.findById(any())).thenReturn(Optional.of(masterTableData));
        when(masterTableRepository.save(masterTableData)).thenReturn(masterTableData);
        assertEquals(true,interviewerService.markAttendance(1,"Present"));
    }
    @Test
    void getAllSlotsByInterviewer(){
        when(masterTableRepository.getAllSlotsForInterviewer(1)).thenReturn(masterTableSlots);
        assertEquals(masterTableSlots,interviewerService.getAllSlotsByInterviewer(1));
    }


}
