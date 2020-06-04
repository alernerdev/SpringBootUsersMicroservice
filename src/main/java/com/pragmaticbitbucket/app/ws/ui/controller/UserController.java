package com.pragmaticbitbucket.app.ws.ui.controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pragmaticbitbucket.app.ws.exceptions.UserServiceException;
import com.pragmaticbitbucket.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.pragmaticbitbucket.app.ws.ui.model.request.UserDetailsRequestModel;
import com.pragmaticbitbucket.app.ws.ui.model.response.UserRest;
import com.pragmaticbitbucket.app.ws.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    Map<String, UserRest> users;

    @Autowired
    UserService userService;

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
    public ResponseEntity<UserRest> getUser(@PathVariable String publicUserId) {
        if (users.containsKey(publicUserId))
            return new ResponseEntity<UserRest>(users.get(publicUserId), HttpStatus.OK);
        else
            return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = userService.createUser(userDetails);
        return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
    }

    @PutMapping(
            path="/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {
        UserRest storedUserDetails = users.get(userId);
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
