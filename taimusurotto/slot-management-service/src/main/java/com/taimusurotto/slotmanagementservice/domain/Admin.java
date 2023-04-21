package com.taimusurotto.slotmanagementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @SequenceGenerator(name = "adminSeq", sequenceName = "adminSeq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "adminSeq")
    int admin_id;
    @Column(unique = true)
    String email;
    @Column(unique = true)
    String userName;
    String password;
}
