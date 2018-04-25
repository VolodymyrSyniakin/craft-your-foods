package com.gmail.vsyniakin.config;

import com.gmail.vsyniakin.model.entity.*;
import com.gmail.vsyniakin.model.enums.RoleUser;

import com.gmail.vsyniakin.services.interfaces.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class AddAdmin {

    @Autowired
    private UserDataService userDataService;
    @Autowired

    @PostConstruct
    public void addUsers() {

        UserData userData = new UserData("Vsynyakin@gmail.com", new BCryptPasswordEncoder().encode("pass"), RoleUser.ADMIN);
        userData.addUser(new UserAccount("admin"));
        userDataService.add(userData);
    }
}
