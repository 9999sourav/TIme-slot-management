package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.*;
import com.taimusurotto.slotmanagementservice.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class QuestionsServiceImplTest {

    @Mock
    AdminRepository adminRepository;

    @Mock
    SlotRepository slotRepository;

    @Mock
    InterviewerRepository interviewerRepository;

    @Mock
    MasterTableRepository masterTableRepository;

    @Mock
    IntervieweeRepository intervieweeRepository;

    @InjectMocks
    QuestionsServiceImpl questionsService;


    Admin admin;
    Slot slot;
    Interviewer interviewer;
    MasterTable masterTableData;
    List<Slot> slots;
    List<Integer> slot_ids, interviewee_ids;
    List<MasterTable> masterTableSlots;
//    Set<Integer> interviewerIds;
    Interviewee interviewee;




    @BeforeEach
    public void setUp(){
        admin = new Admin(1,"Mehul","Admin@1234",null);
        slot = new Slot(1, LocalDate.now(), LocalTime.now(),LocalTime.now(),null,5,0,3);
        interviewer = new Interviewer(1,"Mehul","Mahajan","Mahajan214@gmail.com",null,9682101021L);
        interviewee = new Interviewee(1,"Mehul","Mahajan","Mahajan213@gmail.com",9682101021L);
        masterTableData = new MasterTable(1,null,null,slot,"Open",null,null,null,null);
        slots = new ArrayList<>();
        slots.add(slot);
        slot_ids = new ArrayList<>();
        slot_ids.add(slot.getSlot_Id());
        masterTableSlots = new ArrayList<>();
        masterTableSlots.add(masterTableData);
        interviewee_ids = new ArrayList<>();
        interviewee_ids.add(interviewee.getIntervieweeID());
//        interviewerIds = new HashSet<>();
//        interviewerIds.add(interviewer.getInterviewerID());
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
    void totalCandidatesThatCanBEInterviewed() {
        when(slotRepository.findSlotIdsForSlotDate(LocalDate.now())).thenReturn(slot_ids);
        when(masterTableRepository.countAllCandidates(1)).thenReturn(1);
        assertEquals(1,questionsService.totalCandidatesThatCanBEInterviewed(LocalDate.now()));
    }

    @Test
    void totalNumberOfSlotsForSlotDate() {
        when(slotRepository.findSlotIdsForSlotDate(LocalDate.now())).thenReturn(slot_ids);
        assertEquals(1,questionsService.totalNumberOfSlotsForSlotDate(LocalDate.now()));
    }

    @Test
    void totalNumberOfInterviewerAvailable() {
        when(slotRepository.findSlotIdsForSlotDate(LocalDate.now())).thenReturn(slot_ids);
        when(masterTableRepository.findInterviewerAvailable(1)).thenReturn(interviewee_ids);
        assertEquals(1,questionsService.totalNumberOfInterviewerAvailable(LocalDate.now()));
    }

    @Test
    void slotUtilization() {
        when(slotRepository.getSlotsByDate(LocalDate.now())).thenReturn(slots);
        when(masterTableRepository.findAllBySlotId(1)).thenReturn(masterTableSlots);
        Map<String, Integer> data = new HashMap<>();
        data.put("Bookings",0);
        data.put("Available",1);
        data.put("cancelled",0);
        data.put("Utilization",0);

        assertEquals(data,questionsService.slotUtilization(LocalDate.now()));
    }

    @Test
    void singleSlotStats() {
        when(masterTableRepository.findAllBySlotId(1)).thenReturn(masterTableSlots);
        Map<String, Integer> data = new HashMap<>();
        data.put("Bookings",0);
        data.put("Available",1);
        data.put("cancelled",0);
        data.put("Utilization",0);
        assertEquals(data,questionsService.singleSlotStats(1));
    }

    @Test
    void totalCancelledSlotsForADay(){
        when(slotRepository.findSlotIdsForSlotDate(any())).thenReturn(slot_ids);
        when(masterTableRepository.findNumberOfCancelledSlotsForSlotId(anyInt())).thenReturn(4);
        assertEquals(4,questionsService.totalCancelledSlotsForADay(LocalDate.now()));
    }
}