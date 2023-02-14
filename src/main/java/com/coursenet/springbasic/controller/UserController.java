package com.coursenet.springbasic.controller;

import com.coursenet.springbasic.dto.UserLoginResponseDTO;
import com.coursenet.springbasic.dto.UserRequestDTO;
import com.coursenet.springbasic.dto.UserResponseDTO;
import com.coursenet.springbasic.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> createUsers(@RequestBody UserRequestDTO userRequest){
        log.info(userRequest.toString());
        return userService.createUser(userRequest);
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers(
            @RequestParam(value="id", required=false) Long id,
            @RequestParam(value="name", required=false) String name
    ){
        return userService.getUsers(id,name);
    }

    //Register
    @PostMapping("/users/registration")
    public ResponseEntity<UserResponseDTO> userRegistration(@RequestBody UserRequestDTO userRequest){
        log.info("User Registration Started: "+ userRequest.toString());
        return userService.userRegistration(userRequest);
    }

    //Login
    @PostMapping("/users/login")
    public ResponseEntity<UserLoginResponseDTO> userLogin(@RequestBody UserRequestDTO userRequest){
        log.info("User Login Started: "+userRequest.toString());
        return userService.userLogin(userRequest);
    }
}
