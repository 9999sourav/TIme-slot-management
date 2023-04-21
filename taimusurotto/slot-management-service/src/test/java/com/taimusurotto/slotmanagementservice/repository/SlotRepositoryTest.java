package com.taimusurotto.slotmanagementservice.repository;

import com.taimusurotto.slotmanagementservice.domain.Slot;
import com.taimusurotto.slotmanagementservice.repositories.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SlotRepositoryTest {

    //All Data used is in compliance to the database data entered for testing
    //Adjust values while testing again with new database

    @Autowired
    SlotRepository slotRepository;

    Slot slot;

    @BeforeEach
    public void setUp(){
        slot = new Slot(0, LocalDate.now(), LocalTime.now(),LocalTime.now(),null,4,0,3);
    }

    @Test
    void findSlot() {
        assertEquals(1,slotRepository.findSlot(LocalDate.of(2023,02,15),LocalTime.of(14,23,12),LocalTime.of(14,53,12)).getSlot_Id());
        assertEquals(3,slotRepository.findSlot(LocalDate.of(2023,02,15),LocalTime.of(14,23,12),LocalTime.of(14,53,12)).getLimit());
    }

    @Test
    void findSlotById() {
        assertEquals(LocalDate.of(2023,02,15),slotRepository.findSlotById(1).getSlot_date());
    }

    @Test
    void getSlotsByDate() {
        assertEquals(6,slotRepository.getSlotsByDate(LocalDate.of(2023,02,15)).size());
        assertEquals(2,slotRepository.getSlotsByDate(LocalDate.of(2023,02,20)).size());
    }

    @Test
    void findSlotIdsForSlotDate() {
        List<Integer> slot_ids = new ArrayList<>();
        slot_ids.add(1);
        slot_ids.add(2);
        slot_ids.add(3);
        slot_ids.add(4);
        slot_ids.add(5);
        slot_ids.add(6);
        assertEquals(slot_ids,slotRepository.findSlotIdsForSlotDate(LocalDate.of(2023,02,15)));
    }

    @Test
    void checkBasicFunctionalities(){
        assertEquals(LocalDate.now(),slotRepository.save(slot).getSlot_date());
        assertEquals(LocalDate.of(2023,02,15),slotRepository.findById(1).get().getSlot_date());
    }
}