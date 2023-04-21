package com.taimusurotto.slotmanagementservice.services;


import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.exceptions.InterviewerDoesNotExist;
import com.taimusurotto.slotmanagementservice.exceptions.MaxInterviewersReached;
import com.taimusurotto.slotmanagementservice.exceptions.SlotDoesNotExist;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
public interface InterviewerService {

     boolean addSlotAvailability(List<Integer> slot_Id,int interviewerId) throws SlotDoesNotExist, MaxInterviewersReached;
     boolean markAttendance(int dataId,String remarks);
     List<MasterTable> getAllSlotsByInterviewer(int interviewerId);
     List<Slot> getSlotsByDate(LocalDate date, int interviewerId);
     MasterTable cancelSlot(int slot_id, int interviewer_id, String reason) throws InterviewerDoesNotExist, SlotDoesNotExist, MessagingException, IOException;
}
