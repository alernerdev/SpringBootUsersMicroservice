package com.pragmaticbitbucket.app.ws.userservice;

import com.pragmaticbitbucket.app.ws.ui.model.request.UserDetailsRequestModel;
import com.pragmaticbitbucket.app.ws.ui.model.response.UserRest;

public interface UserService {
    UserRest createUser(UserDetailsRequestModel userDetails);
}
