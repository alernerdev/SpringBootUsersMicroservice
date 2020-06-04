package com.pragmaticbitbucket.app.ws.userservice.impl;

import com.pragmaticbitbucket.app.ws.ui.model.request.UserDetailsRequestModel;
import com.pragmaticbitbucket.app.ws.ui.model.response.UserRest;
import com.pragmaticbitbucket.app.ws.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.pragmaticbitbucket.app.ws.shared.Utils;

@Service
public class UserServiceImpl implements UserService {

    Map<String, UserRest> users;
    Utils utils;

    public UserServiceImpl() {

    }

    // autowired so the Utils gets created
    @Autowired
    public UserServiceImpl(Utils utils) {
        this.utils = utils;
    }

    @Override
    public UserRest createUser(UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());
        returnValue.setEmail(userDetails.getEmail());

        if (users == null)
            users = new HashMap<>();

        String userId = utils.generateUserId();
        returnValue.setUserId(userId);
        users.put(userId, returnValue);
        return returnValue;
    }
}
