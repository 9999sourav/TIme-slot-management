package com.taimusurotto.slotmanagementservice.repository;

import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import com.taimusurotto.slotmanagementservice.repositories.InterviewerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class         InterviewerRepositoryTest {
    @Autowired
    InterviewerRepository interviewerRepository;

    Interviewer interviewer;
    @BeforeEach
    public  void setup(){
        this.interviewer = new Interviewer();
        interviewer.setFirstName("sourav");
        interviewer.setLastName("Mohanty");
        interviewer.setEmail("sourav@gmail.com");
        interviewer.setPhoneNumber(750454987);

    }
    @AfterEach
    public  void tearDown(){
        this.interviewer=null;
        interviewerRepository.deleteAll();
    }

    // JUnit test for save Interviewer operation
    @Test
    public void givenInterviewerObject_whenSave_thenReturnSavedInterviewer(){

        Interviewer savedInterviewer= interviewerRepository.save(interviewer);

        assertEquals(interviewer.getFirstName(),savedInterviewer.getFirstName());
    }
    // JUnit test for get all Interviewer operation

    @Test
    public void givenInterviewersList_whenFindAll_thenInterviewersList(){
        interviewerRepository.save(interviewer);
        Interviewer interviewer1=new Interviewer();
        interviewer1.setFirstName("mehul");
        interviewer1.setLastName("mahajan");
        interviewer1.setEmail("mehul@gmail.com");
        interviewer1.setPhoneNumber(423546);
        interviewerRepository.save(interviewer1);

        // when -  action or the behaviour that we are going test
        List<Interviewer> interviewerList = interviewerRepository.findAll();

        // then - verify the output
        assertEquals(2,interviewerList.size());

    }
    // JUnit test for get Interviewer by id operation

    @Test
    public void givenInterviewerObject_whenFindById_thenReturnInterviewerObject(){

        interviewer = interviewerRepository.save(interviewer);

        // when -  action or the behaviour that we are going test
        Interviewer interviewer1 = interviewerRepository.findById(interviewer.getInterviewerID()).get();

        // then - verify the output
        assertThat(interviewer1).isNotNull();
    }

    // JUnit test for update Interviewer operation

    @Test
    public void givenInterviewerObject_whenUpdateEmployee_thenReturnUpdatedInterviewer(){

        interviewerRepository.save(interviewer);

        // when -  action or the behaviour that we are going test
        Interviewer savedInterviewer = interviewerRepository.findById(interviewer.getInterviewerID()).get();
        savedInterviewer.setEmail("ram@gmail.com");
        Interviewer updatedInterviewee =  interviewerRepository.save(savedInterviewer);

        // then - verify the output
        assertEquals(updatedInterviewee.getEmail(),"ram@gmail.com");
    }

    // JUnit test for delete Interviewer operation
    @Test
    public void givenInterviewerObject_whenDelete_thenRemoveInterviewer(){

        interviewer=interviewerRepository.save(interviewer);

        // when -  action or the behaviour that we are going test
        interviewerRepository.deleteById(interviewer.getInterviewerID());
        Optional<Interviewer> interviewerOptional = interviewerRepository.findById(interviewer.getInterviewerID());

        // then - verify the output
        assertEquals(Optional.empty(),interviewerOptional);
    }
}
