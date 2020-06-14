package com.pragmaticbitbucket.app.ws.ui.controller;

import com.pragmaticbitbucket.app.ws.shared.UserDto;
import com.pragmaticbitbucket.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.pragmaticbitbucket.app.ws.ui.model.request.UserDetailsRequestModel;
import com.pragmaticbitbucket.app.ws.ui.model.response.UserDetailsResponseModel;
import com.pragmaticbitbucket.app.ws.userservice.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("users")
public class UserController {

    Map<String, UserDetailsResponseModel> users;

    @Autowired
    UserService userService;

    @GetMapping("/status/check")
    public String status() {
        return "Users working...";
    }

    @GetMapping
    // when required=false, does not work with primitive datatypes since they cant be NULL
    public String getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "limit", defaultValue = "50") int limit,
                           @RequestParam(value = "sort", required = false) String sort) {
        return "GET users called ";
    }

    @GetMapping(
            path = "/{publicUserId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserDetailsResponseModel> getUser(@PathVariable String publicUserId) {
        if (users.containsKey(publicUserId))
            return new ResponseEntity<UserDetailsResponseModel>(users.get(publicUserId), HttpStatus.OK);
        else
            return new ResponseEntity<UserDetailsResponseModel>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserDetailsResponseModel> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);

        UserDetailsResponseModel returnValue = modelMapper.map(createdUser, UserDetailsResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue); // 201 created
    }

    @PutMapping(
            path="/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserDetailsResponseModel updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {
        UserDetailsResponseModel storedUserDetails = users.get(userId);
        storedUserDetails.setFirstName(userDetails.getFirstName());
        storedUserDetails.setLastName(userDetails.getLastName());
        return storedUserDetails;
    }

    @DeleteMapping(
            path="/{userId}"
    )
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        users.remove(userId);
        return ResponseEntity.noContent().build(); // 204 response
    }
}
