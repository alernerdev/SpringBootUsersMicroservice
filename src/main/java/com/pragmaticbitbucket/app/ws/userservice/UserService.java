package com.pragmaticbitbucket.app.ws.userservice;

import com.pragmaticbitbucket.app.ws.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

// UserDetailsService has loadUserByUsername which is used by security framework
public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
}
