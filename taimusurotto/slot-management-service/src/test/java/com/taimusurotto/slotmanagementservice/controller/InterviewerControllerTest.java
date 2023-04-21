package com.taimusurotto.slotmanagementservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taimusurotto.slotmanagementservice.domain.Interviewee;
import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.services.InterviewerService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class InterviewerControllerTest {
    @Mock
    InterviewerService interviewerService;

    @InjectMocks
    InterviewerController interviewerController;

    MockMvc mockMvc;
    Interviewer interviewer;
    Interviewee interviewee;
    Slot slot;
    List<Slot> slots;
    String slotsJSON;
    MasterTable masterTableData;
    List<Integer> slot_ids;
    List<MasterTable> masterTableSlots;
    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(interviewerController).build();
        interviewer = new Interviewer(0, "Mehul","Mahajan","Mahajan214@gmail.com","Interviewer@1234",9682101021L);
        slot = new Slot(0, LocalDate.now(), LocalTime.now(),LocalTime.now(),null,5,0,3);
        slots = new ArrayList<>();
        slots.add(slot);
        slotsJSON = ObjectListToJson(slots);
        interviewee=new Interviewee(1,"sourav","mohanty","sourav@gmail.com",105235);
        masterTableData= new MasterTable();
        masterTableData.setData_Id(1);
        masterTableData.setInterviewer(interviewer);
        masterTableData.setSlot_id(slot);
        masterTableData.setStatus("Booked");
        masterTableData.setInterviewee(interviewee);
        masterTableSlots=new ArrayList<>();
        masterTableSlots.add(masterTableData);
        slot_ids = new ArrayList<>();
        slot_ids.add(slot.getSlot_Id());
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
    void addSlot() throws Exception {
        when(interviewerService.addSlotAvailability(slot_ids,1)).thenReturn(true);
        mockMvc.perform(post("http://localhost:4400/interviewer/addslots/1")
                .contentType(MediaType.APPLICATION_JSON).content(ObjTojson
                        (slot_ids))).andExpect(status().isOk()).andDo
                (MockMvcResultHandlers.print());
    }
    @Test
    void cancelOpenSlot() throws Exception {
        when(interviewerService.cancelSlot(anyInt(),anyInt(),any())).thenReturn(masterTableData);
        mockMvc.perform(post("http://localhost:4400/interviewer/cancelSlot/"+1+"/"+1)
                .contentType(MediaType.APPLICATION_JSON).content(ObjTojson
                        ("Cancelling"))).andExpect(status().isOk()).andDo
                (MockMvcResultHandlers.print());
    }

    @Test
    void  addRemarks() throws Exception {
        when(interviewerService.markAttendance(anyInt(),any())).thenReturn(true);
        mockMvc.perform(post("http://localhost:4400/interviewer/addremarks/"+1)
                .contentType(MediaType.APPLICATION_JSON).content(ObjTojson
                        ("Present"))).andExpect(status().isOk()).andDo
                (MockMvcResultHandlers.print());
    }
    @Test
    void getAllSlots() throws Exception {
        when(interviewerService.getAllSlotsByInterviewer(1)).thenReturn(masterTableSlots);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/interviewer/allSlots/1")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

    }
    @Test
    void  getSlotsByDate() throws Exception {
        when(interviewerService.getSlotsByDate(LocalDate.now(),anyInt())).thenReturn(slots);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/interviewer/getSlots/"+LocalDate.now())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());


    }
}
