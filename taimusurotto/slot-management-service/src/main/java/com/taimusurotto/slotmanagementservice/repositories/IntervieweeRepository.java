package com.taimusurotto.slotmanagementservice.repositories;

import com.taimusurotto.slotmanagementservice.domain.Interviewee;
import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


public interface IntervieweeRepository extends JpaRepository<Interviewee,Integer> {

    @Query(value = "select first_name from interviewee where intervieweeid=?1",nativeQuery = true)
    String findNameForId(int id);

    Interviewee findByEmail(String email);

}
