package com.taimusurotto.slotmanagementservice.repository;

import com.taimusurotto.slotmanagementservice.domain.Admin;
import com.taimusurotto.slotmanagementservice.domain.Interviewee;
import com.taimusurotto.slotmanagementservice.repositories.AdminRepository;
import com.taimusurotto.slotmanagementservice.repositories.IntervieweeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminRepositoryTest {
    @Autowired
    AdminRepository adminRepository;

    Admin admin;

    @BeforeEach
    public  void setup(){
        this.admin = new Admin();
        admin.setUserName("sourav");
        admin.setPassword("Password");
        admin.setAdmin_id(1);

    }
    @AfterEach
    public  void tearDown(){
        this.admin=null;
        adminRepository.deleteAll();
    }

    @Test
    void  getAdminById(){
        adminRepository.save(admin);
        assertEquals(Optional.of(admin),adminRepository.findById(1));
    }
    @Test
    void deleteAdmin(){
        adminRepository.save(admin);
        adminRepository.deleteById(admin.getAdmin_id());
        assertEquals(Optional.empty(),adminRepository.findById(admin.getAdmin_id()));
    }

    @Test
    void updateAdmin(){
        adminRepository.save(admin);
        admin.setPassword("New Password");
        adminRepository.save(admin);
        assertEquals("New Password", adminRepository.findById(1).get().getPassword());
    }


}
