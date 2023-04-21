package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.repositories.IntervieweeRepository;
import com.taimusurotto.slotmanagementservice.repositories.MasterTableRepository;
import com.taimusurotto.slotmanagementservice.repositories.SlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class QuestionsServiceImpl implements QuestionsService{
    MasterTableRepository masterTableRepository;
    SlotRepository slotRepository;
    IntervieweeRepository intervieweeRepository;
    @Autowired
    public QuestionsServiceImpl(MasterTableRepository masterTableRepository, SlotRepository slotRepository,IntervieweeRepository intervieweeRepository){
        this.masterTableRepository=masterTableRepository;
        this.slotRepository=slotRepository;
        this.intervieweeRepository=intervieweeRepository;
    }

    @Override
    public String CountCandidatesInterviewedForSlotId(int slot_id, LocalDate slot_date) {
        int count=0;
        String result;
        List<Integer> slotList=slotRepository.findSlotIdsForSlotDate(slot_date);
        log.info("Slot List for "+slot_date+" --> "+slotList);

        for(int slot:slotList){
            if(slot==slot_id){
                count=masterTableRepository.countBookedCandidates(slot_id);
                log.info("Number of Candidates giving interview on "+slot_date+" for "+slot_id+"--->"+String.valueOf(count));
            }
        }
        result="Number of Candidates giving interview on "+slot_date+" for slot Id "+slot_id+"--->"+count;
        if(count==0){
            result="On "+slot_date+" there is no booking for slot Id - "+slot_id;
        }
        return result;
    }
    @Override
    public String nameOfIntervieweesForSlotId(int slot_id,LocalDate slot_date) {
        List<Integer> slotList=slotRepository.findSlotIdsForSlotDate(slot_date);
        log.info("Slot List for "+slot_date+" --> "+slotList);
        List<Integer> intervieweeIds =null;
        List<String> nameList = new ArrayList<>();
        String result;

        for(int slot:slotList){
            if(slot==slot_id){
                intervieweeIds=masterTableRepository.findIntervieweeIdOfCandidatesForSlotId(slot_id);
                log.info("Interviewee id of candidate -"+String.valueOf(intervieweeIds));
            }
        }
        if(!intervieweeIds.isEmpty()) {
            for (int id : intervieweeIds) {
                String name=intervieweeRepository.findNameForId(id);
                nameList.add(name);
            }
        }
        result="Name of interviewees ---> "+nameList+" Booked for slot - "+slot_id;
        return result;
    }
    @Override
    public int totalCandidatesThatCanBEInterviewed(LocalDate slot_date) {
        int count=0;
        int sum=0;
        List<Integer> slotList=slotRepository.findSlotIdsForSlotDate(slot_date);
        log.info("Slot List for "+slot_date+" --> "+slotList);

        for(int slot:slotList){
            count=masterTableRepository.countAllCandidates(slot);
            log.info("number of candidates that have given interviewee for slot id - "+slot+" is ->"+count);
            sum=sum+count;
        }
        return sum;
    }
    @Override
    public int totalNumberOfSlotsForSlotDate(LocalDate slot_date) {
        List<Integer> slotList=slotRepository.findSlotIdsForSlotDate(slot_date);
        return slotList.size();
    }
    @Override
    public int totalNumberOfInterviewerAvailable(LocalDate slot_date) {
        List<Integer> interviewerIds;
        List<Integer> slotList=slotRepository.findSlotIdsForSlotDate(slot_date);
        Set<Integer> uniqueInterviewer=new HashSet<>();
        log.info("Slot List for "+slot_date+" --> "+slotList);
        for(int slot:slotList){
            interviewerIds=masterTableRepository.findInterviewerAvailable(slot);
            log.info("Interviewer Ids for Slot id - "+slot+" are "+interviewerIds);
            for (int id : interviewerIds) {
                uniqueInterviewer.add(id);
            }
        }
        log.info("ids of interviewer"+uniqueInterviewer);
        return uniqueInterviewer.size();
    }
    public Map<String, Integer> slotUtilization(LocalDate slot_date) {
        log.info("Getting utilization for slots on : "+slot_date);
        List<Slot> slots = slotRepository.getSlotsByDate(slot_date);
        log.info(slots.toString());
        int totalSlots = slots.size();
        log.info("Total Slots : "+totalSlots);
        List<Integer> slotIdsForDate = new ArrayList<>();
        for(Slot s : slots){
            slotIdsForDate.add(s.getSlot_Id());
        }
        int bookedSlots =0 ,availableSlots = 0, cancelledSlots=0;
        for (int id : slotIdsForDate){
            List<MasterTable> slotsOnDate = masterTableRepository.findAllBySlotId(id);
            for (MasterTable masterTable : slotsOnDate){
                if (masterTable.getStatus().equalsIgnoreCase("open") || masterTable.getStatus().equalsIgnoreCase("open/f"))
                    availableSlots++;
                else if (masterTable.getStatus().equalsIgnoreCase("booked") || masterTable.getStatus().equalsIgnoreCase("booked/f"))
                    bookedSlots++;
                else if (masterTable.getStatus().equalsIgnoreCase("cancelled") || masterTable.getStatus().equalsIgnoreCase("cancelled/f"))
                    cancelledSlots++;
            }
        }
        Map<String, Integer> data = new HashMap<>();
        data.put("Bookings",bookedSlots);
        data.put("Available",availableSlots);
        data.put("cancelled",cancelledSlots);
        if(totalSlots==0){
            data.put("Utilization",0);
        }
        else{
            data.put("Utilization",bookedSlots/totalSlots);
        }
        return data;
    }
    @Override
    public Map<String, Integer> singleSlotStats(int slot_id){
        List<MasterTable> allBookings = masterTableRepository.findAllBySlotId(slot_id);
        int booked = 0;
        int total = allBookings.size();
        int cancelled = 0;
        int available = 0;
        for (MasterTable masterTable : allBookings){
            if (masterTable.getStatus().equalsIgnoreCase("open") || masterTable.getStatus().equalsIgnoreCase("open/f"))
                available++;
            else if (masterTable.getStatus().equalsIgnoreCase("booked") || masterTable.getStatus().equalsIgnoreCase("booked/f"))
                booked++;
            else if (masterTable.getStatus().equalsIgnoreCase("cancelled") || masterTable.getStatus().equalsIgnoreCase("cancelled/f"))
                cancelled++;
        }
        Map<String, Integer> data = new HashMap<>();
        int utilization = booked / total;
        data.put("Bookings",booked);
        data.put("Available",available);
        data.put("cancelled",cancelled);
        data.put("Utilization",utilization);
        return data;
    }

    @Override
    public int totalCancelledSlotsForADay(LocalDate slot_date) {
        int sum=0;
        List<Integer> slotList=slotRepository.findSlotIdsForSlotDate(slot_date);
        for(int slot:slotList){
            int result=masterTableRepository.findNumberOfCancelledSlotsForSlotId(slot);
            sum=sum+result;
        }
        return sum;
    }
}