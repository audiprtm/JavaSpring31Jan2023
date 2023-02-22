package com.coursenet.springbasic.dto;

import com.coursenet.springbasic.entity.Users;
import lombok.Data;

@Data
public class UserResponseDTO {
    public UserResponseDTO(Users user) {
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
    }

    private String userName;
    private String userEmail;
}
