package com.taimusurotto.slotmanagementservice.repository;

import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.repositories.MasterTableRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MasterTableRepositoryTest {

    //All Data used is in compliance to the database data entered for testing
    //Adjust values while testing again with new database

    @Autowired
    MasterTableRepository masterTableRepository;

    MasterTable masterTable;
    Slot slot;
    Interviewer interviewer;


    @BeforeEach
    void setUp() {
        slot= new Slot(0,LocalDate.now(),LocalTime.now(),LocalTime.now(),null,5,2,3);
        interviewer = new Interviewer(0,"Mehul","Mahajan","Mahajan214@gmail.com",null,9682101021L);
        masterTable = new MasterTable(0,null,interviewer,slot,"Open",null,null,null,null);
    }

    @AfterEach
    void tearDown() {
        slot = null;
        interviewer = null;
        masterTable = null;
    }

    @Test
    void findAvailableSlots() {
        assertEquals(1,masterTableRepository.findAvailableSlots().size());
    }

    @Test
    void findUnBookedSlot() {
        assertEquals(1,masterTableRepository.FindUnBookedSlot(107).size());
    }

    @Test
    void findByIntervieweeID() {
        assertEquals("Mehul",masterTableRepository.findByIntervieweeID(0).get(0).getInterviewee().getFirstName());
    }

    @Test
    void findBookedSlotByInterviewee() {
        assertEquals("Booked",masterTableRepository.findBookedSlotByInterviewee(1,57).getStatus());
    }

    @Test
    void findBookedSlot() {
        assertEquals("Mehul",masterTableRepository.findBookedSlot(3,57).getInterviewer().getFirstName());
    }

    @Test
    void findAllBookedInterviews() {
        assertEquals(1,masterTableRepository.findAllBookedInterviews(3).size());
    }

    @Test
    void findOpenSlotBySlotId() {
        assertEquals(3,masterTableRepository.findOpenSlotBySlotId(107).get(0).getInterviewer().getInterviewerID());
    }

    @Test
    void findAllBySlotId() {
        assertEquals(1, masterTableRepository.findAllBySlotId(57).size());
        assertEquals(1, masterTableRepository.findAllBySlotId(107).size());
    }

    @Test
    void findSlotDetailsForSlotId() {
        assertEquals(1, masterTableRepository.findSlotDetailsForSlotId(57).size());
        assertEquals(1, masterTableRepository.findSlotDetailsForSlotId(107).size());
    }

    @Test
    void countBookedCandidates() {
        assertEquals(1,masterTableRepository.countBookedCandidates(57));
        assertEquals(0,masterTableRepository.countBookedCandidates(107));
    }

    @Test
    void findIntervieweeIdOfCandidatesForSlotId() {
        assertEquals(1,masterTableRepository.findIntervieweeIdOfCandidatesForSlotId(57).size());
        assertEquals(0,masterTableRepository.findIntervieweeIdOfCandidatesForSlotId(107).size());
    }

    @Test
    void countAllCandidates(){
        assertEquals(1,masterTableRepository.countAllCandidates(57));
        assertEquals(1,masterTableRepository.countAllCandidates(107));
    }

    @Test
    void findInterviewerAvailable() {
        assertEquals(1,masterTableRepository.findInterviewerAvailable(57).size());
    }

    @Test
    void countAllInterviewers() {
        assertEquals(1,masterTableRepository.countAllInterviewers(57));
    }

    @Test
    void getAllSlotsForInterviewer() {
        assertEquals(3, masterTableRepository.getAllSlotsForInterviewer(3).size());
    }

    @Test
    void getSlot() {
        assertEquals("Shivani",masterTableRepository.getSlot(57,3).getInterviewee().getFirstName());
        assertEquals("Mehul",masterTableRepository.getSlot(107,3).getInterviewer().getFirstName());
    }

    @Test
    void findBookedSlotByIntervieweeId() {
        assertEquals("Shivani",masterTableRepository.findBookedSlotByIntervieweeId(1).getInterviewee().getFirstName());
        assertNull(masterTableRepository.findBookedSlotByIntervieweeId(0));
    }

    @Test
    void checkBasicJpaFunctions(){
        assertEquals(masterTable,masterTableRepository.save(masterTable));
        assertEquals("Mehul",masterTableRepository.findById(352).get().getInterviewer().getFirstName());
    }

    @Test
    void checkFindNumberOfCancelledSlotsForSlotId(){
        assertEquals(0,masterTableRepository.findNumberOfCancelledSlotsForSlotId(57));
        assertEquals(1,masterTableRepository.findNumberOfCancelledSlotsForSlotId(103));
    }

}