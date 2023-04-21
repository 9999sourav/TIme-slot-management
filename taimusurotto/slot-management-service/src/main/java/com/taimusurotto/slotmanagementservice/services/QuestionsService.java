package com.taimusurotto.slotmanagementservice.services;

import java.time.LocalDate;
import java.util.Map;

public interface QuestionsService {
    public String CountCandidatesInterviewedForSlotId(int slot_id, LocalDate slot_date);
    public String nameOfIntervieweesForSlotId(int slot_id,LocalDate slot_date);
    public int totalCandidatesThatCanBEInterviewed(LocalDate slot_date);
    public int totalNumberOfSlotsForSlotDate(LocalDate slot_date);
    public int totalNumberOfInterviewerAvailable(LocalDate slot_date);
    public Map<String, Integer> slotUtilization(LocalDate slot_date);
    public Map<String, Integer> singleSlotStats(int slot_id);
    public int totalCancelledSlotsForADay(LocalDate slot_date);
}
