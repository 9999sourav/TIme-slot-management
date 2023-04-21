package com.taimusurotto.slotmanagementservice.repositories;

import com.taimusurotto.slotmanagementservice.domain.FreezeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface FreezeTimeRepository extends JpaRepository<FreezeTime, Number> {

    @Query(value = "select * from freeze_time where freeze_date=?1",nativeQuery = true)
    FreezeTime findFreezeTimeForDate(LocalDate freeze_date);

}
