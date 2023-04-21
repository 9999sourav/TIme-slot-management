package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.*;
import com.taimusurotto.slotmanagementservice.exceptions.IntervieweeAlreadyExistsException;
import com.taimusurotto.slotmanagementservice.exceptions.IntervieweeNotFound;
import com.taimusurotto.slotmanagementservice.exceptions.SlotDoesNotExist;
import com.taimusurotto.slotmanagementservice.exceptions.SlotNotAvailableException;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IntervieweeServiceTest {
//    @Mock
    AdminRepository adminRepository;

    @Mock
    SlotRepository slotRepository;

    @Mock
    InterviewerRepository interviewerRepository;
    @Mock
    IntervieweeRepository intervieweeRepository;

    @Mock
    MasterTableRepository masterTableRepository;
    @Mock
    IVideoChatService videoChatService;

    @InjectMocks
    IntervieweeServiceImpl intervieweeService;

    Admin admin;
    Slot slot;
    Interviewer interviewer;
    Interviewee interviewee;
    MasterTable masterTableData;
    MasterTable getMasterTableDataAfterCancel;
    MasterTable bookedSlot;
    List<Slot> slots;
    List<Integer> slot_ids;
    List<MasterTable> masterTableSlots;

    @BeforeEach
    public void setUp(){
        slot = new Slot(1, LocalDate.now(), LocalTime.now(),LocalTime.now(),null,5,0,3);
        slotRepository.save(slot);
        interviewer = new Interviewer(1,"Mehul","Mahajan","Mahajan214@gmail.com",null,9682101021L);
        interviewerRepository.save(interviewer);
        interviewee=new Interviewee(1,"sourav","mohanty","sourav@gmail.com",105235);
        intervieweeRepository.save(interviewee);
        masterTableData= new MasterTable();
        masterTableData.setData_Id(1);
        masterTableData.setInterviewer(interviewer);
        masterTableData.setSlot_id(slot);
        masterTableData.setStatus("Open");
        slot_ids = new ArrayList<>();
        slot_ids.add(slot.getSlot_Id());
        masterTableSlots=new ArrayList<>();
        masterTableSlots.add(masterTableData);
        getMasterTableDataAfterCancel=new MasterTable();
        getMasterTableDataAfterCancel.setInterviewer(interviewer);
        getMasterTableDataAfterCancel.setSlot_id(slot);
        getMasterTableDataAfterCancel.setStatus("Open");
        bookedSlot = new MasterTable(2,interviewee,interviewer,slot,"Booked",null,null,null,null);

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
    void availableSlots() throws SlotNotAvailableException {
        when(masterTableRepository.findAvailableSlots()).thenReturn(masterTableSlots);
        String date =String.valueOf(LocalDate.now().getYear())+"-0"+String.valueOf(LocalDate.now().getMonthValue())+"-"+ String.valueOf(LocalDate.now().getDayOfMonth());
        assertEquals(masterTableSlots,intervieweeService.availableSlotsByDate(date));


    }
    @Test
    void bookASlot() throws SlotNotAvailableException, MessagingException, IntervieweeNotFound, IntervieweeAlreadyExistsException, IOException {
        when(masterTableRepository.findByIntervieweeID(1)).thenReturn(Collections.emptyList());
        when(masterTableRepository.FindUnBookedSlot(1)).thenReturn(masterTableSlots);
        when(intervieweeRepository.findById(1)).thenReturn(Optional.of(interviewee));
        when(masterTableRepository.save(masterTableData)).thenReturn(masterTableData);
        assertEquals(masterTableData,intervieweeService.bookASlot(1,1));
    }

    @Test
    void cancelASlot() throws IntervieweeNotFound, SlotDoesNotExist, MessagingException, IOException {
        when(slotRepository.findById(1)).thenReturn(Optional.of(slot));
        when(masterTableRepository.findByIntervieweeID(1)).thenReturn(masterTableSlots);
        when(masterTableRepository.findBookedSlotByInterviewee(1,1)).thenReturn(masterTableData);
        when(masterTableRepository.save(masterTableData)).thenReturn(masterTableData);
        assertEquals(getMasterTableDataAfterCancel,intervieweeService.cancelASlot(1,1,"reason"));
    }

    @Test
    void addInterviewee(){
        when(intervieweeRepository.save(interviewee)).thenReturn(interviewee);
        assertEquals(interviewee,intervieweeService.addInterviewee(interviewee));
    }

    @Test
    void availableSlotsByDate(){
        when(masterTableRepository.findAvailableSlots()).thenReturn(masterTableSlots);
        assertEquals(masterTableSlots,intervieweeService.availableSlotsByDate(LocalDate.now().toString()));
    }

    @Test
    void getBookedSlot(){
        when(masterTableRepository.findBookedSlotByIntervieweeId(1)).thenReturn(bookedSlot);
        assertEquals(bookedSlot,intervieweeService.getBookedSlot(1));

    }






}
