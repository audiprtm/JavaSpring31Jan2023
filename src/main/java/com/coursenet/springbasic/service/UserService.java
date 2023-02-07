package com.coursenet.springbasic.service;

import com.coursenet.springbasic.dto.UserRequestDTO;
import com.coursenet.springbasic.dto.UserResponseDTO;
import com.coursenet.springbasic.entity.Users;
import com.coursenet.springbasic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<UserResponseDTO> createUser(UserRequestDTO userRequest){
        Users user = new Users();
        user.setUserName(userRequest.getUserName());
        user.setUserEmail(userRequest.getUserEmail());

        user = userRepository.save(user);

        UserResponseDTO userResponse = new UserResponseDTO(user);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    public ResponseEntity<List<UserResponseDTO>> getUsers(Long id, String name) {
        List<UserResponseDTO> listUserResponseDTO = new ArrayList<>();

        if (id==null){
            List<Users> listUsers = userRepository.findAll();

            for (int i=0; i<listUsers.size();i++){
                UserResponseDTO userResponseDTO = new UserResponseDTO(listUsers.get(i));
                listUserResponseDTO.add(userResponseDTO);
            }
        }else{
            Optional<Users> user = userRepository.findById(id);
            if (!user.isPresent()){
                return new ResponseEntity<>(listUserResponseDTO, HttpStatus.NOT_FOUND);
            }
            UserResponseDTO userResponseDTO = new UserResponseDTO(user.get());
            listUserResponseDTO.add(userResponseDTO);
        }
        return new ResponseEntity<>(listUserResponseDTO, HttpStatus.OK);
    }
}
