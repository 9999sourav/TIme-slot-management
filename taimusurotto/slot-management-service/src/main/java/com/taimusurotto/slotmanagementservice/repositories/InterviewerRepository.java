package com.taimusurotto.slotmanagementservice.repositories;

import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterviewerRepository extends JpaRepository<Interviewer, Integer> {
    @Query( value = "select * from interviewer u where email = ?1",
            nativeQuery = true)
    public Interviewer findByEmail(String email);
}
