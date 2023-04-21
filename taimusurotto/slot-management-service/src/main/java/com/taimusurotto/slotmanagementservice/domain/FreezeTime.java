package com.taimusurotto.slotmanagementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreezeTime {
    @Id
    @SequenceGenerator(name = "freezeSeq", sequenceName = "freezeSeq")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "freezeSeq")
    Integer id;
    @Column(unique = true)
    LocalDate freeze_date;
    int freeze_time;
}
