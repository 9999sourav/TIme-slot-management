package com.taimusurotto.slotmanagementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MasterTable")
public class MasterTable {
    @Id
    @SequenceGenerator(name = "masterSeq", sequenceName = "masterSeq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "masterSeq")
    private int data_Id;

    @ManyToOne( cascade = CascadeType.DETACH)
    @JoinColumn(name = "intervieweeID")
    private Interviewee interviewee;
    @ManyToOne( cascade = CascadeType.DETACH)
    @JoinColumn(name = "interviewerID")
    private Interviewer interviewer;
    @ManyToOne( cascade = CascadeType.DETACH)
    @JoinColumn(name = "slot_Id")
    private Slot slot_id;
    String status, canceled_by, remarks, link,password;
}
