package com.taimusurotto.slotmanagementservice.controller;

import com.taimusurotto.slotmanagementservice.domain.LoginData;
import com.taimusurotto.slotmanagementservice.services.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController
{
    @Autowired
    LoginServiceImpl loginService;


    @PostMapping("/verifyCreds")
    public ResponseEntity<?> Login(@RequestBody LoginData loginData){
        try {
            return new ResponseEntity<>(loginService.login(loginData), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/resetPassword/{id}/{role}")
    public ResponseEntity<?> resetPassword(@PathVariable int id, @PathVariable String role, @RequestBody String password){
        try{
            return new ResponseEntity<>(loginService.resetPassword(id,role,password),HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

}
