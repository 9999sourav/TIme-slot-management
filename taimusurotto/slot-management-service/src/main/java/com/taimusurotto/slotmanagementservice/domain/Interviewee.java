package com.taimusurotto.slotmanagementservice.domain;

import com.taimusurotto.slotmanagementservice.repositories.MasterTableRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "interviewee")
public class Interviewee {
    @Id
    @SequenceGenerator(name = "intervieweeSeq", sequenceName = "intervieweeSeq")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "intervieweeSeq")
    Integer intervieweeID;
    String firstName;
    String lastName;
    @Column(unique=true)
    String email;
    long applicationID;
//    @OneToMany(mappedBy = "interviewee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<MasterTableRepository> masterTableRepositorySet =  new HashSet<>();
}