package com.Service;

import com.Config.MessageStrings;
import com.DTO.Users.SignInDto;
import com.DTO.Users.SignInResponseDto;
import com.DTO.Users.SignUpResponseDto;
import com.DTO.Users.SignupDto;
import com.Repository.UserRepository;
import com.exceptions.AuthenticationFailException;
import com.exceptions.CustomException;
import com.model.AuthenticationToken;
import com.model.User;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(UserService.class);
    public SignUpResponseDto signUp(SignupDto signupDto) throws CustomException {
        //check to see if the email address exist or not
        if(Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))){
            throw new CustomException("user already exists");
        }

        //if it exists then we will check for password
        String encryptedPassword = signupDto.getPassword();
        try{
            encryptedPassword = hashPassword(signupDto.getPassword());
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        //if both r correct (new user with correct password)
        User u = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(),encryptedPassword);
        try {
            userRepository.save(u);
            // generate token for user
            final AuthenticationToken authenticationToken = new AuthenticationToken(u);
            // save token in database
            authenticationService.saveConfirmationToken(authenticationToken);
            return new SignUpResponseDto("success", "user created successfully");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }


}
