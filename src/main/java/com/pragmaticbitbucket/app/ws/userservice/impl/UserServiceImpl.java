package com.pragmaticbitbucket.app.ws.userservice.impl;

import com.pragmaticbitbucket.app.ws.data.UserEntity;
import com.pragmaticbitbucket.app.ws.data.UserRepository;
import com.pragmaticbitbucket.app.ws.shared.UserDto;
import com.pragmaticbitbucket.app.ws.userservice.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.pragmaticbitbucket.app.ws.shared.Utils;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private Utils utils;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            Utils utils) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.utils = utils;
    }


    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(utils.generateUserId());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userRepository.save(userEntity);

        // UserDto is returned
        return modelMapper.map(userDetails, UserDto.class);
    }

    // this gets called by security framework to lookup if user exists
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);

        // User implements UserDetails interface
        return new User(
                userEntity.getEmail(),
                userEntity.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);

        return new ModelMapper().map(userEntity, UserDto.class);

    }
}
