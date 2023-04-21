package com.taimusurotto.slotmanagementservice.controller;

import com.taimusurotto.slotmanagementservice.services.AdminServiceImpl;
import com.taimusurotto.slotmanagementservice.services.QuestionsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class QuestionsControllerTest {

    @Mock
    QuestionsServiceImpl questionsService;

    @InjectMocks
    QuestionsController questionsController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(questionsController).build();
    }

    @Test
    void countCandidatesGivingIntervieweeForSlotDate() throws Exception {
        when(questionsService.CountCandidatesInterviewedForSlotId(anyInt(),any())).thenReturn("Candidates : "+ 2);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/questions/count/"+String.valueOf(3)+"/"+LocalDate.now())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void nameOfCandidatesGivingIntervieweeForSlotDate() throws Exception {
        when(questionsService.nameOfIntervieweesForSlotId(anyInt(),any())).thenReturn("Name Retrieve Test");
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/questions/name/"+String.valueOf(3)+"/"+LocalDate.now())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void totalCandidatesGivingIntervieweeForSlotDate() throws Exception {
        when(questionsService.totalCandidatesThatCanBEInterviewed(any())).thenReturn(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/questions/name/"+LocalDate.now())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void totalSlotsForSlotDate() throws Exception {
        when(questionsService.totalNumberOfSlotsForSlotDate(any())).thenReturn(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/questions/count/"+LocalDate.now())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void totalInterviewerAvailable() throws Exception {
        when(questionsService.totalNumberOfInterviewerAvailable(any())).thenReturn(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/questions/interviewer/"+LocalDate.now())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

//    @Test
//    void getSlotUtilization() throws Exception {
//        when(questionsService.slotUtilization(any())).thenReturn("Booked : 5, Cancelled : 3, Open : 1");
//        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/questions/getUtilization/"+LocalDate.now())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
//    }

    @Test
    void getSingleSlotStats() throws Exception {
//        when(questionsService.singleSlotStats(anyInt())).thenReturn("Booked : 5, Cancelled : 3, Open : 1");
//        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/questions/getUtilizationBySlot/"+2)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getCancelledSlots() throws Exception{
        when(questionsService.totalCancelledSlotsForADay(any())).thenReturn(4);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/questions/cancelledSlots/"+LocalDate.now())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}