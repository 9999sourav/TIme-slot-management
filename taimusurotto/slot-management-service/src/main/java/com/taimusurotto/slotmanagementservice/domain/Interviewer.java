package com.taimusurotto.slotmanagementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "interviewer")
public class Interviewer {
    @Id
    @SequenceGenerator(name = "interviewerSeq", sequenceName = "interviewerSeq")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "interviewerSeq")
    Integer interviewerID;
    String firstName;
    String lastName;
    @Column(unique = true)
    String email;
    String password;
    long phoneNumber;
}
