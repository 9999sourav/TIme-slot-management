package com.taimusurotto.slotmanagementservice.repositories;

import com.taimusurotto.slotmanagementservice.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    @Query( value = "select * from admin u where user_name = ?1",
            nativeQuery = true)
    public Admin findByUsername(String username);

    @Query( value = "select * from admin u where email = ?1",
            nativeQuery = true)
    public Admin findByEmail(String email);

}
