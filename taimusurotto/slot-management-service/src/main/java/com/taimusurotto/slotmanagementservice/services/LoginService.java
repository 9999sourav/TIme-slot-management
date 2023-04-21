package com.taimusurotto.slotmanagementservice.services;

import com.taimusurotto.slotmanagementservice.domain.LoginData;
import com.taimusurotto.slotmanagementservice.exceptions.InvalidCredentials;
import com.taimusurotto.slotmanagementservice.exceptions.UserNotFound;

import java.util.Map;

public interface LoginService {
    Map<String,String> login(LoginData loginData) throws InvalidCredentials, UserNotFound;
    boolean resetPassword(int id, String role, String password) throws UserNotFound;
}
