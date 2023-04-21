package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.Admin;
import com.taimusurotto.slotmanagementservice.domain.Interviewer;
import com.taimusurotto.slotmanagementservice.domain.LoginData;
import com.taimusurotto.slotmanagementservice.exceptions.InvalidCredentials;
import com.taimusurotto.slotmanagementservice.exceptions.UserNotFound;
import com.taimusurotto.slotmanagementservice.repositories.AdminRepository;
import com.taimusurotto.slotmanagementservice.repositories.InterviewerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService{

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Override
    public Map<String, String> login(LoginData loginData) throws InvalidCredentials, UserNotFound {
        Map<String, String> userData = new HashMap<>();
        if (adminRepository.findByEmail(loginData.getEmail())!=null){
            log.info("Admin Login : ");
            Admin admin = adminRepository.findByEmail(loginData.getEmail());
            log.info(String.valueOf(admin));
            if (admin.getPassword().equals(loginData.getPassword())) {
                userData.put("Name", admin.getUserName());
                userData.put("Email", admin.getEmail());
                userData.put("Id", String.valueOf(admin.getAdmin_id()));
                userData.put("Role","Admin");
                return userData;
            }
            else {
                throw new InvalidCredentials();
            }
        }
        else if (interviewerRepository.findByEmail(loginData.getEmail())!=null){
            log.info("Interviewer Login : ");
            Interviewer interviewer = interviewerRepository.findByEmail(loginData.getEmail());
            if (interviewer.getPassword().equals(loginData.getPassword())){
                userData.put("Name",interviewer.getFirstName()+" "+interviewer.getLastName());
                userData.put("Email",interviewer.getEmail());
                userData.put("Id",String.valueOf(interviewer.getInterviewerID()));
                userData.put("Role","Interviewer");
                return userData;
            }
            else {
                throw new InvalidCredentials();
            }
        }
        throw new UserNotFound();
    }

    @Override
    public boolean resetPassword(int id, String role, String password) throws UserNotFound {
        if(role.equalsIgnoreCase("Admin")){
            if (adminRepository.findById(id).isEmpty()){
                throw new UserNotFound();
            }
            Admin admin = adminRepository.findById(id).get();
            admin.setPassword(password);
            adminRepository.save(admin);
            return true;
        }
        else if (role.equalsIgnoreCase("Interviewer")){
            if(interviewerRepository.findById(id).isEmpty()){
                throw new UserNotFound();
            }
            Interviewer interviewer = interviewerRepository.findById(id).get();
            interviewer.setPassword(password);
            interviewerRepository.save(interviewer);
            return true;
        }
        return false;
    }
}
