package com.pragmaticbitbucket.app.ws.userservice.impl;

import com.pragmaticbitbucket.app.ws.data.AlbumsServiceClient;
import com.pragmaticbitbucket.app.ws.data.UserEntity;
import com.pragmaticbitbucket.app.ws.data.UserRepository;
import com.pragmaticbitbucket.app.ws.exceptions.UserServiceException;
import com.pragmaticbitbucket.app.ws.shared.UserDto;
import com.pragmaticbitbucket.app.ws.ui.model.response.AlbumsResponseModel;
import com.pragmaticbitbucket.app.ws.userservice.UserService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.pragmaticbitbucket.app.ws.shared.Utils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Utils utils;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    // private RestTemplate restTemplate;
    AlbumsServiceClient albumsServiceClient;
    private Environment env;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            // RestTemplate restTemplate,
            AlbumsServiceClient albumsServiceClient,
            Environment env,
            Utils utils) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.utils = utils;
        // this.restTemplate = restTemplate;
        this.albumsServiceClient = albumsServiceClient;
        this.env = env;
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

    @Override
    public UserDto getUserDetailsByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new UsernameNotFoundException(userId);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        // RestTemplate approach
        /*
        String albumsUrl = String.format(env.getProperty("albums.url"), userId);
        // where albums-ws is the name of the service to look up in eureka
        ResponseEntity<List<AlbumsResponseModel>> albumsListResponse =
                restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumsResponseModel>>() {
                });
        List<AlbumsResponseModel> albumsList = albumsListResponse.getBody();
         */

        // feign client approach
        List<AlbumsResponseModel> albumsList=null;

        /* instead of try/catch, I am now using FeignErrorDecoder */

        //try {
             albumsList = albumsServiceClient.getAlbums(userId);
        //} catch (FeignException e) {
        //    logger.error(e.getLocalizedMessage());
        //}

        userDto.setAlbums(albumsList);
        return userDto;
    }
}
