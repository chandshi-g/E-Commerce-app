package com.Controller;

import com.DTO.Users.SignInDto;
import com.DTO.Users.SignInResponseDto;
import com.DTO.Users.SignUpResponseDto;
import com.DTO.Users.SignupDto;
import com.Service.UserService;
import com.exceptions.AuthenticationFailException;
import com.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public SignUpResponseDto signup(@RequestBody SignupDto signupDto) throws CustomException{
        return userService.signUp(signupDto);
    }
    @PostMapping("/signIn")
    public SignInResponseDto Signup(@RequestBody SignInDto signInDto) throws CustomException, AuthenticationFailException {
        return userService.signIn(signInDto);
    }
}
