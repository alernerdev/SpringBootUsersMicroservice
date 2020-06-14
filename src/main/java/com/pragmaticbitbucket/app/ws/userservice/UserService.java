package com.pragmaticbitbucket.app.ws.userservice;

import com.pragmaticbitbucket.app.ws.shared.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDetails);
}
