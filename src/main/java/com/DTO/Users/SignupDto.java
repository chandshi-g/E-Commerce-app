package com.DTO.Users;

import lombok.Data;

@Data
public class SignupDto {//creates a new Account
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
