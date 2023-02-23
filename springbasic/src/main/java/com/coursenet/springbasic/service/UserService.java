package com.coursenet.springbasic.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.coursenet.springbasic.dto.UserLoginResponseDTO;
import com.coursenet.springbasic.dto.UserRequestDTO;
import com.coursenet.springbasic.dto.UserResponseDTO;
import com.coursenet.springbasic.entity.Users;
import com.coursenet.springbasic.repository.UserRepository;
import com.coursenet.springbasic.security.JWTUtil;
import com.coursenet.springbasic.security.TokenConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Value("${salt.hash.password}")
    private String salt;

    @Autowired
    private JWTUtil jwtUtil;

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

    public ResponseEntity<UserResponseDTO> userRegistration(UserRequestDTO userRequest) {
        Users users = new Users();
        users.setUserName(userRequest.getUserName());
        users.setUserEmail(userRequest.getUserEmail());
        users.setPassword(hashPassword(userRequest.getPassword()));

        userRepository.save(users);

        UserResponseDTO userResponseDTO = new UserResponseDTO(users);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    private String hashPassword(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public ResponseEntity<UserLoginResponseDTO> userLogin(UserRequestDTO userRequest) {
        //Find userName and Password, Exist or Not
        Optional<Users> user = userRepository.findByUserNameAndPassword(
                userRequest.getUserName(),hashPassword(userRequest.getPassword()));

        if (!user.isPresent()){
            log.error("Not found");
            return new ResponseEntity<>(new UserLoginResponseDTO(), HttpStatus.FORBIDDEN);
        }

        String accessToken= jwtUtil.generateJWTToken(
                user.get().getUserName(), TokenConstants.TOKEN_ACCESS);

        String refreshToken= jwtUtil.generateJWTToken(
                user.get().getUserName(),TokenConstants.TOKEN_REFRESH);

        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
        userLoginResponseDTO.setAccessToken(accessToken);
        userLoginResponseDTO.setRefreshToken(refreshToken);

        return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.OK);
    }

    public ResponseEntity<UserLoginResponseDTO> refreshToken(String refreshToken) {
        DecodedJWT decodedJWT = jwtUtil.verifyJWTToken(refreshToken);
        if (decodedJWT.getClaim("type").asString().equals("REFRESH")){
            String accessToken= jwtUtil.generateJWTToken(
                    decodedJWT.getSubject(),"ACCESS");

            UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
            userLoginResponseDTO.setAccessToken(accessToken);
            userLoginResponseDTO.setRefreshToken(refreshToken);

            return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(new UserLoginResponseDTO(), HttpStatus.FORBIDDEN);
    }
}
