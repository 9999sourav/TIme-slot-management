package com.taimusurotto.slotmanagementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "slot")
public class Slot {
    @Id
    @SequenceGenerator(name = "slotSeq", sequenceName = "slotSeq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "slotSeq")
    private Integer slot_Id;
    LocalDate slot_date;
    LocalTime slot_start_Time;
    LocalTime slot_end_Time;
    String status;
    int limit, bookings, no_of_available_interviewers;
//    @OneToMany(mappedBy = "interviewee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<MasterTable> masterTableSet = new HashSet<>();
}
