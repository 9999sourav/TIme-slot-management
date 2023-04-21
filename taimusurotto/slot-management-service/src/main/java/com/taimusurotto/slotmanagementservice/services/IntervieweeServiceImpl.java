package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.SlotManagementServiceApplication;
import com.taimusurotto.slotmanagementservice.domain.Interviewee;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.dto.ZoomMeetingObjectDTO;
import com.taimusurotto.slotmanagementservice.exceptions.IntervieweeAlreadyExistsException;
import com.taimusurotto.slotmanagementservice.exceptions.IntervieweeNotFound;
import com.taimusurotto.slotmanagementservice.exceptions.SlotDoesNotExist;
import com.taimusurotto.slotmanagementservice.exceptions.SlotNotAvailableException;
import com.taimusurotto.slotmanagementservice.repositories.IntervieweeRepository;
import com.taimusurotto.slotmanagementservice.repositories.MasterTableRepository;
import com.taimusurotto.slotmanagementservice.repositories.SlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Service
public class IntervieweeServiceImpl implements IntervieweeService {
    Logger logger = LoggerFactory.getLogger(SlotManagementServiceApplication.class);
    MasterTableRepository masterTableRepository;
    IntervieweeRepository intervieweeRepository;
    private final SlotRepository slotRepository;

    @Autowired
    IVideoChatService videoChatService;

    @Autowired
    private EmailSenderService senderService;

    @Autowired
    public IntervieweeServiceImpl(MasterTableRepository masterTableRepository, IntervieweeRepository intervieweeRepository,
                                  SlotRepository slotRepository) {
        this.masterTableRepository = masterTableRepository;
        this.intervieweeRepository = intervieweeRepository;
        this.slotRepository = slotRepository;
    }

    @Override
    public MasterTable bookASlot(int interviewee_Id, int slot_Id) throws IntervieweeAlreadyExistsException, IntervieweeNotFound, SlotNotAvailableException, MessagingException, IOException {
        if (intervieweeRepository.findById(interviewee_Id).isEmpty()) {
            logger.error("Interviewee does not exists in interviewee table");
            throw new IntervieweeNotFound();
        } else if (!masterTableRepository.findByIntervieweeID(interviewee_Id).isEmpty()) {
            logger.info(String.valueOf(masterTableRepository.findByIntervieweeID(interviewee_Id)));
            logger.error("Interviewee have already booked a slot previously");
            throw new IntervieweeAlreadyExistsException("Interviewee have already booked a slot");
        }
       else if (masterTableRepository.FindUnBookedSlot(slot_Id).isEmpty()) {
            logger.error("No Slot is Available for Booking");
            throw new SlotNotAvailableException("No Slot is available");
        } else {
            ZoomMeetingObjectDTO zoomMeetingObjectDTO = new ZoomMeetingObjectDTO();
            ZoomMeetingObjectDTO zoomMeetingObjectDTO2 = videoChatService.createMeeting(zoomMeetingObjectDTO);
            String join_url= zoomMeetingObjectDTO2.getJoin_url();
            String password = zoomMeetingObjectDTO2.getPassword();



            Interviewee interviewee = intervieweeRepository.findById(interviewee_Id).get();
            List<MasterTable> openSlots = masterTableRepository.FindUnBookedSlot(slot_Id);
            logger.info("Available slots from master table ---> " + openSlots);
            MasterTable openSlot = openSlots.get(0);

            logger.info("Interviewee Details ---> " + String.valueOf(interviewee));
            logger.info("Available slots from master table ---> " + openSlots);
            logger.info("Before Booking master table  ---> " + openSlot);

            openSlot.setInterviewee(interviewee);
            openSlot.setStatus("Booked");
            Slot slot = slotRepository.findById(openSlot.getSlot_id().getSlot_Id()).get();
            slot.setBookings(slot.getBookings()+1);
            slotRepository.save(slot);
            openSlot.setLink(join_url);
            openSlot.setPassword(password);
            masterTableRepository.save(openSlot);
            try {
                senderService.sendEmailFromTemplate1(openSlot, "Slot Booked");
            }
            catch (Exception e){
                //logic to catch failed email jobs
                logger.error("Error=>"+e.getMessage());
            }
            logger.info("After Booking master table  ---> " + openSlot);

            return openSlot;
        }
    }
    @Override
    public MasterTable cancelASlot(int interviewee_Id, int slot_Id, String reason) throws IntervieweeNotFound, SlotDoesNotExist, MessagingException, IOException {
        if (slotRepository.findById(slot_Id).isEmpty()) {
            logger.error(slot_Id + " does not exists in slot table");
            throw new SlotDoesNotExist();
        } else if (masterTableRepository.findByIntervieweeID(interviewee_Id).isEmpty()) {
            logger.error("Interviewee does not exists in master table");
            throw new IntervieweeNotFound();
        } else if (masterTableRepository.findBookedSlotByInterviewee(interviewee_Id, slot_Id) == null) {
            logger.error("No slot is Booked for this interviewee (" + interviewee_Id + ")");
            throw new IntervieweeNotFound();
        } else {
            MasterTable bookedSlot = masterTableRepository.findBookedSlotByInterviewee(interviewee_Id, slot_Id);
            logger.info("Before deleting ---> "+bookedSlot);
            bookedSlot.setStatus("Cancelled");
            bookedSlot.setCanceled_by("Interviewee");
            reason = reason.substring(1, reason.length()-1);
            bookedSlot.setRemarks(reason);
            masterTableRepository.save(bookedSlot);
            logger.info("After deleting ---> "+bookedSlot);
            Slot slot = slotRepository.findById(bookedSlot.getSlot_id().getSlot_Id()).get();
            slot.setBookings(slot.getBookings()-1);
            slotRepository.save(slot);
            MasterTable newRow = new MasterTable();
            newRow.setStatus("Open");
            newRow.setInterviewer(bookedSlot.getInterviewer());
            newRow.setSlot_id(bookedSlot.getSlot_id());
            masterTableRepository.save(newRow);
            logger.info("New added row ---> "+newRow);
            try {
                senderService.sendEmailFromTemplate2(bookedSlot, "Slot Cancelled");
            }
            catch (Exception e){
                logger.info("Failed to send email");
                // logic to save failed emails to a table and re run
            }
            return newRow;
        }
    }

    @Override
    public Interviewee addInterviewee(Interviewee interviewee) {
        if(intervieweeRepository.findByEmail(interviewee.getEmail())!=null){
            return intervieweeRepository.findByEmail(interviewee.getEmail());
        }
        else {

            return intervieweeRepository.save(interviewee);
        }

    }

    @Override
    public List<MasterTable> availableSlotsByDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(date, formatter);
        List<MasterTable> availableSlots =masterTableRepository.findAvailableSlots();
        logger.info(String.valueOf(availableSlots));
        List<MasterTable> filterDate = new ArrayList<>();
        for(MasterTable mt : availableSlots){
            if(mt.getSlot_id().getSlot_date().equals(date1)){
                System.out.println(mt);
                filterDate.add(mt);
            }
        }
        int index = 0;
        List<Integer> indexes = new ArrayList<>();
        if (!filterDate.isEmpty()){
            for (MasterTable masterTable : filterDate){
                if (masterTable.getSlot_id().getBookings()==masterTable.getSlot_id().getLimit() ||
                    masterTable.getSlot_id().getNo_of_available_interviewers()==masterTable.getSlot_id().getBookings()){
                    indexes.add(index);
                }
                index++;
            }
        }
        logger.info(String.valueOf(filterDate));
        if (!indexes.isEmpty()){
            int deleteIndex = 0;
            for (int i : indexes){
                int a = i-deleteIndex;
                filterDate.remove(a);
                deleteIndex++;

            }
        }
        logger.info(String.valueOf(filterDate));
        return  filterDate;
    }

    @Override
    public MasterTable getBookedSlot(int intervieweeId) {
       return masterTableRepository.findBookedSlotByIntervieweeId(intervieweeId);
    }

    @Override
    public List<MasterTable> availableSlots() throws SlotNotAvailableException {
        if (masterTableRepository.findAvailableSlots() == null) {
            logger.error("No Slot is Available for Booking");
            throw new SlotNotAvailableException("No Slot is available");
        }
        List<MasterTable> availableSlots = masterTableRepository.findAvailableSlots();
        logger.info("Available Slots ---> "+availableSlots.toString());
        return availableSlots;
    }


}
