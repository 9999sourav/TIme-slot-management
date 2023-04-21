package com.taimusurotto.slotmanagementservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taimusurotto.slotmanagementservice.domain.Admin;
import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.exceptions.SlotDoesNotExist;
import com.taimusurotto.slotmanagementservice.services.AdminServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    AdminServiceImpl adminService;

    @InjectMocks
    AdminController adminController;

    Admin admin;
    MockMvc mockMvc;
    Interviewer interviewer;
    Slot slot;
    List<Slot> slots;
    List<Integer> slot_ids;
    String slotsJSON;
    List<Interviewer> interviewerList;
    MasterTable masterTable;
    List<MasterTable> masterTables;
    List<Admin> admins;


    @BeforeEach
    public void setUp(){
        admin = new Admin(0,"Raghav","Mahajan","Admin@1234");
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        interviewer = new Interviewer(0, "Mehul","Mahajan","Mahajan214@gmail.com","Interviewer@1234",9682101021L);
        slot = new Slot(0, null, null, null,null,5,0,3);
        masterTable = new MasterTable(0,null, interviewer, slot,null,null,null,null,null);
        slots = new ArrayList<>();
        slots.add(slot);
        slotsJSON = ObjectListToJson(slots);
        slot_ids = new ArrayList<>();
        slot_ids.add(slot.getSlot_Id());
        interviewerList = new ArrayList<>();
        interviewerList.add(interviewer);
        masterTables = new ArrayList<>();
        masterTables.add(masterTable);
        admins = new ArrayList<>();
        admins.add(admin);
    }

    private static String ObjectListToJson(List<?> list){
        String result = "[";
        for (Object obj : list){
            String data = ObjTojson(obj);
            result = result.concat(data + ",");
        }
        result = result.substring(0,result.length()-1);
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
    void addAdmin() throws Exception {
        when(adminService.addAdmin(admin)).thenReturn(admin);
        mockMvc.perform(post("http://localhost:4400/admin/addAdmin").contentType(MediaType.APPLICATION_JSON).content(ObjTojson(admin))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void addInterviewer() throws Exception {
        when(adminService.addInterviewer(interviewer)).thenReturn(interviewer);
        mockMvc.perform(post("http://localhost:4400/admin/addInterviewer").contentType(MediaType.APPLICATION_JSON).content(ObjTojson(interviewer))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteInterviewer() throws Exception {
        when(adminService.deleteInterviewer(anyInt())).thenReturn("Deleted");
        mockMvc.perform(delete("http://localhost:4400/admin/deleteInterviewer/"+1)).andExpect(status().isNoContent()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    void createSlots() throws Exception {
        when(adminService.createSlot(slots)).thenReturn(slots);
        mockMvc.perform(post("http://localhost:4400/admin/createSlots").contentType(MediaType.APPLICATION_JSON).content(slotsJSON)).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteSlots() throws Exception {
        when(adminService.deleteSlot(slot_ids)).thenReturn(slot_ids);
        mockMvc.perform(delete("http://localhost:4400/admin/deleteSlots").contentType(MediaType.APPLICATION_JSON).content(ObjectListToJson(slot_ids))).andExpect(status().isNoContent()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getAllInterviewers() throws Exception {
        when(adminService.getAllInterviewers()).thenReturn(interviewerList);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:4400/admin/getAllInterviewers")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getSlots() throws Exception {
        when(adminService.getSlotsForAParticularDay(any())).thenReturn(slots);
        mockMvc.perform(get("http://locahost:4400/admin/"+LocalDate.now())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteSlot() throws Exception {
        when(adminService.deleteSlotById(anyInt())).thenReturn(true);
        mockMvc.perform(delete("http://localhost:4400/admin/delete/"+1)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getAllSlots() throws Exception {
        when(adminService.getAllSlots()).thenReturn(masterTables);
        mockMvc.perform(get("http://localhost:4400/admin/getAllSlots")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getAllDetails() throws Exception {
        when(adminService.getAllAdminDetails()).thenReturn(admins);
        mockMvc.perform(get("http://localhost:4400/admin/getAllAdminDetails")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteAdmin() throws Exception {
        when(adminService.DeleteAdminById(anyInt())).thenReturn(true);
        mockMvc.perform(delete("http://localhost:4400/admin/deleteAdmin/"+1)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}