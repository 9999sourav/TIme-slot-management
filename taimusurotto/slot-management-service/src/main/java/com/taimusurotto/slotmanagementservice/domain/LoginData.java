package com.taimusurotto.slotmanagementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginData {
    @Id
   String email;
   String password;
}
