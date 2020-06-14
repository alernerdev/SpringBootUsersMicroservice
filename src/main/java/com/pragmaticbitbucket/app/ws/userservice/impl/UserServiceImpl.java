package com.pragmaticbitbucket.app.ws.userservice.impl;

import com.pragmaticbitbucket.app.ws.data.UserEntity;
import com.pragmaticbitbucket.app.ws.data.UserRepository;
import com.pragmaticbitbucket.app.ws.shared.UserDto;
import com.pragmaticbitbucket.app.ws.userservice.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pragmaticbitbucket.app.ws.shared.Utils;

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
}
