package com.taimusurotto.slotmanagementservice.repositories;

import com.taimusurotto.slotmanagementservice.domain.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Number> {
    @Query( value = "SELECT * FROM slot u WHERE slot_date = ?1 AND slot_start_time =?2 AND slot_end_time = ?3",
            nativeQuery = true)
    public Slot findSlot(LocalDate date, LocalTime start, LocalTime end);

    @Query( value = "SELECT * FROM slot u WHERE slot_id=?1",
            nativeQuery = true)
    public Slot findSlotById(int slotId);

    @Query( value = "SELECT * FROM slot u WHERE slot_date=?1",
            nativeQuery = true)
    public List<Slot> getSlotsByDate(LocalDate date);


    //question queries
    @Query(value = "select slot_id from slot where slot_date=?1",nativeQuery = true)
    List<Integer> findSlotIdsForSlotDate(LocalDate slot_date);

}

