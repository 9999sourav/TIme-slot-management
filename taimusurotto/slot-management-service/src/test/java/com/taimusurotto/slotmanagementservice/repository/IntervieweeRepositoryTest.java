package com.taimusurotto.slotmanagementservice.repository;

import com.taimusurotto.slotmanagementservice.domain.Interviewee;
import com.taimusurotto.slotmanagementservice.repositories.IntervieweeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntervieweeRepositoryTest {
    @Autowired
    IntervieweeRepository intervieweeRepository;

    Interviewee interviewee;

    @BeforeEach
    public  void setup(){
       this.interviewee = new Interviewee();
       interviewee.setFirstName("sourav");
       interviewee.setLastName("Mohanty");
       interviewee.setEmail("sourav@gmail.com");
       interviewee.setApplicationID(105124);

    }
    @AfterEach
    public  void tearDown(){
       this.interviewee=null;
       intervieweeRepository.deleteAll();
    }

    // JUnit test for save interviewee operation
    @Test
    public void givenIntervieweeObject_whenSave_thenReturnSavedInterviewee(){

        Interviewee savedInterviewee= intervieweeRepository.save(interviewee);

        assertEquals(interviewee.getFirstName(),savedInterviewee.getFirstName());
    }
    // JUnit test for get all Interviewee operation

    @Test
    public void givenIntervieweesList_whenFindAll_thenIntervieweesList(){
        intervieweeRepository.save(interviewee);
        Interviewee interviewee1=new Interviewee();
        interviewee1.setFirstName("mehul");
        interviewee1.setLastName("mahajan");
        interviewee1.setEmail("mehul@gmail.com");
        interviewee1.setApplicationID(423546);
        intervieweeRepository.save(interviewee1);

        // when -  action or the behaviour that we are going test
        List<Interviewee> intervieweeList = intervieweeRepository.findAll();

        // then - verify the output
        assertEquals(2,intervieweeList.size());

    }
    // JUnit test for get interviewee by id operation

    @Test
    public void givenIntervieweeObject_whenFindById_thenReturnIntervieweeObject(){

        interviewee = intervieweeRepository.save(interviewee);

        // when -  action or the behaviour that we are going test
        Interviewee interviewee1 = intervieweeRepository.findById(interviewee.getIntervieweeID()).get();

        // then - verify the output
        assertThat(interviewee1).isNotNull();
    }

    // JUnit test for update Interviewee operation

    @Test
    public void givenIntervieweeObject_whenUpdateEmployee_thenReturnUpdatedInterviewee(){

        intervieweeRepository.save(interviewee);

        // when -  action or the behaviour that we are going test
        Interviewee savedInterviewee = intervieweeRepository.findById(interviewee.getIntervieweeID()).get();
        savedInterviewee.setEmail("ram@gmail.com");
        Interviewee updatedInterviewee =  intervieweeRepository.save(savedInterviewee);

        // then - verify the output
        assertEquals(updatedInterviewee.getEmail(),"ram@gmail.com");
    }

    // JUnit test for delete Interviewee operation
    @Test
    public void givenIntervieweeObject_whenDelete_thenRemoveInterviewee(){

        interviewee=intervieweeRepository.save(interviewee);

        // when -  action or the behaviour that we are going test
        intervieweeRepository.deleteById(interviewee.getIntervieweeID());
        Optional<Interviewee> intervieweeOptional = intervieweeRepository.findById(interviewee.getIntervieweeID());

        // then - verify the output
        assertEquals(Optional.empty(),intervieweeOptional);
    }
}




