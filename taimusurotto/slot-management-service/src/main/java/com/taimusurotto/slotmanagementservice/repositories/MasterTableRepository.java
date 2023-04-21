package com.taimusurotto.slotmanagementservice.repositories;


import com.taimusurotto.slotmanagementservice.domain.MasterTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MasterTableRepository extends JpaRepository<MasterTable,Number> {
    @Query(value="select * from master_table t where t.status= 'Open'",nativeQuery = true)
    List<MasterTable> findAvailableSlots();
    @Query(value = "select * from master_table where status= 'Open' AND slot_Id= ?1",nativeQuery = true)
    List<MasterTable> FindUnBookedSlot(int slotId);
    @Query(value = "select * from master_table where intervieweeid = ?1 AND status='Booked'",nativeQuery = true)
    List<MasterTable> findByIntervieweeID(int intervieweeId);
    @Query(value = "select * from master_table t where intervieweeid= ?1 AND t.slot_Id= ?2 AND  t.status= 'Booked'",nativeQuery = true)
    public MasterTable findBookedSlotByInterviewee(int intervieweeId, int slotId);
    @Query(value = "select * from master_table t where interviewerid= ?1 AND t.slot_Id= ?2 AND  t.status= 'Booked'",nativeQuery = true)
    public MasterTable findBookedSlot(int interviewerId, int slotId);
    @Query(value = "select * from master_table t where t.interviewerid= ?1 AND  t.status= 'Booked'",nativeQuery = true)
    public List<MasterTable> findAllBookedInterviews(int interviewerId);
    @Query(value = "select * from master_table t where t.slot_Id= ?1 AND  t.status= 'Open'",nativeQuery = true)
    List<MasterTable> findOpenSlotBySlotId(int slotId);

    @Query(value = "select * from master_table t where t.slot_Id= ?1",nativeQuery = true)
    List<MasterTable> findAllBySlotId(int id);
    @Query(value = "select * from master_table where slot_id=?1",nativeQuery = true)
    List<MasterTable> findSlotDetailsForSlotId(int slot_id);



    // queries for questions
    @Query(value = "select count(data_id) from master_table where slot_id=?1 AND status='Booked'",nativeQuery = true)
    int countBookedCandidates(int slot_id);
    @Query(value = "select intervieweeid from master_table where slot_id=?1 AND status='Booked'",nativeQuery = true)
    List<Integer> findIntervieweeIdOfCandidatesForSlotId(int slot_id);
    @Query(value = "select count(data_id) from master_table where slot_id=?1 AND status='Completed'",nativeQuery = true)
    int countAllCandidates(int slot_id);
    @Query(value = "select interviewerid from master_table where slot_id=?1 AND status in('Booked','open')",nativeQuery = true)
    List<Integer> findInterviewerAvailable(int slot_id);
    @Query(value = "select count(interviewerid) from master_table where slot_id=?1",nativeQuery = true)
    int countAllInterviewers(int slotId);

    @Query(value = "select * from master_table t where interviewerid= ?1",nativeQuery = true)
    List<MasterTable> getAllSlotsForInterviewer(int interviewerId);

    @Query(value = "select * from master_table t where slot_id= ?1 AND interviewerid =?2",nativeQuery = true)
    MasterTable getSlot(int slot_id, int interviewer_id);

    @Query(value = "select * from master_table t where intervieweeid= ?1 AND  t.status= 'Booked'",nativeQuery = true)
    public MasterTable findBookedSlotByIntervieweeId(int intervieweeId);

    @Query(value="select count(data_id) from master_table where status='Cancelled' and slot_Id=?1",nativeQuery = true)
    public int findNumberOfCancelledSlotsForSlotId(int slot_Id);

    @Query(value = "select * from master_table t where t.slot_Id= ?1 AND interviewerid=?2",nativeQuery = true)
    List<MasterTable> findAllBySlotForInterviewer(int slot_id, int interviewerid);
}

