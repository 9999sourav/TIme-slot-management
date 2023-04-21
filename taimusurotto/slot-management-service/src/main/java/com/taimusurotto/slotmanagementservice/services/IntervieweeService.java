package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.Interviewee;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.exceptions.IntervieweeAlreadyExistsException;
import com.taimusurotto.slotmanagementservice.exceptions.IntervieweeNotFound;
import com.taimusurotto.slotmanagementservice.exceptions.SlotDoesNotExist;
import com.taimusurotto.slotmanagementservice.exceptions.SlotNotAvailableException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface IntervieweeService {
    public MasterTable bookASlot(int interviewee_Id, int slot_Id) throws SlotNotAvailableException, IntervieweeAlreadyExistsException, IntervieweeNotFound, MessagingException, IOException;
    public MasterTable cancelASlot(int interviewee_Id, int slot_Id, String reason) throws IntervieweeNotFound, SlotDoesNotExist, MessagingException, IOException;
    public Interviewee addInterviewee(Interviewee interviewee);
    public  List<MasterTable> availableSlotsByDate(String date);
    public MasterTable getBookedSlot(int intervieweeId);
    List<MasterTable> availableSlots() throws SlotNotAvailableException;
}
