package com.pragmaticbitbucket.app.ws.ui.controller;

import com.pragmaticbitbucket.app.ws.ui.model.request.UserDetailsRequestModel;
import com.pragmaticbitbucket.app.ws.ui.model.response.UserRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.awt.*;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping
    // when required=false, does not work with primitive datatypes since they cant be NULL
    public String getUsers(@RequestParam(value="page", defaultValue = "1") int page,
                           @RequestParam(value="limit", defaultValue = "50") int limit,
                           @RequestParam(value="sort", required = false) String sort) {
        return "GET users called ";
    }

    @GetMapping(
            path="/{publicUserId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
            )
    public ResponseEntity<UserRest> getUser(@PathVariable String publicUserId) {
        UserRest returnValue = new UserRest();
        returnValue.setFirstName("alex");
        returnValue.setLastName("lerner");
        returnValue.setEmail("a@gmail.com");
        returnValue.setUserId("fbff");

        return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());
        returnValue.setEmail(userDetails.getEmail());

        return returnValue;
    }

    @PutMapping
    public String updateUser() {
        return "update user called";
    }

    @DeleteMapping
    String deleteUser() {
        return "delete user called";
    }
}
