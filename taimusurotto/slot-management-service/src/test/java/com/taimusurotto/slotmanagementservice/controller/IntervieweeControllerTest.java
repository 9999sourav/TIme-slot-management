package com.taimusurotto.slotmanagementservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taimusurotto.slotmanagementservice.domain.*;

import com.taimusurotto.slotmanagementservice.services.IntervieweeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class IntervieweeControllerTest {
    @Mock
    IntervieweeServiceImpl intervieweeService;
    @InjectMocks
    IntervieweeController intervieweeController;
    MockMvc mockMvc;
    Interviewer interviewer;
    Interviewee interviewee;
    Slot slot;
    List<Slot> slots;
    String slotsJSON;
    MasterTable masterTableData;
    MasterTable getMasterTableDataAfterCancel;
    MasterTable bookedSlot;
    List<MasterTable> masterTableSlots;


    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(intervieweeController).build();
        interviewer = new Interviewer(0, "Mehul","Mahajan","Mahajan214@gmail.com",null,9682101021L);
        slot = new Slot(0, LocalDate.now(), LocalTime.now(),LocalTime.now(),null,5,0,3);
        slots = new ArrayList<>();
        slots.add(slot);
        slotsJSON = ObjectListToJson(slots);
        interviewee=new Interviewee(1,"sourav","mohanty","sourav@gmail.com",105235);
        masterTableData= new MasterTable();
        masterTableData.setData_Id(1);
        masterTableData.setInterviewer(interviewer);
        masterTableData.setSlot_id(slot);
        masterTableData.setStatus("Open");
        masterTableSlots=new ArrayList<>();
        masterTableSlots.add(masterTableData);
        getMasterTableDataAfterCancel=new MasterTable();
        getMasterTableDataAfterCancel.setInterviewer(interviewer);
        getMasterTableDataAfterCancel.setSlot_id(slot);
        getMasterTableDataAfterCancel.setStatus("Open");
        bookedSlot = new MasterTable(2,interviewee,interviewer,slot,"Booked",null,null,null,null);

    }

    private static String ObjectListToJson(List<?> list){
        String result = "[";
        for (Object obj : list){
            String data = ObjTojson(obj);
            result = result.concat(data + ",");
        }
        result.substring(0,result.length()-1);
        result = result.concat("]");
        return result;
    }

    private static String ObjTojson(Object ob)
    {
        String result;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch (JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }
    @Test
    void bookASlot() throws Exception {
        when(intervieweeService.bookASlot(1,1)).thenReturn(bookedSlot);
        mockMvc.perform(post("http://localhost:4400/interviewee/book/1")
                .contentType(MediaType.APPLICATION_JSON).content(ObjTojson
                        (1))).andExpect(status().isOk()).andDo
                (MockMvcResultHandlers.print());
    }
    @Test
    void  addTheInterviewee() throws Exception {
        when(intervieweeService.addInterviewee(interviewee)).thenReturn(interviewee);
        mockMvc.perform(post("http://localhost:4400/interviewee")
                .contentType(MediaType.APPLICATION_JSON).content(ObjTojson
                        (interviewee))).andExpect(status().isOk()).andDo
                (MockMvcResultHandlers.print());
    }
    @Test
    void getAvailableSlotsByDate() throws Exception {
        when(intervieweeService.availableSlotsByDate( LocalDate.now().toString())).thenReturn(masterTableSlots);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/interviewee/allSlots/"+LocalDate.now().toString())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());


    }
    @Test
    void getBookedSlotByIntervieweeId() throws Exception {
        when(intervieweeService.getBookedSlot(1)).thenReturn(bookedSlot);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/interviewee/boookedslot/1")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());


    }


}
