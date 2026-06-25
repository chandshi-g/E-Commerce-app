package com.Config;

import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
public class ApiResponse {
    private final Boolean success;
    private final String message;

    public boolean isSuccess(){
        return success;
    }
    public String getMessage(){
        return message;
    }
    public String getTimeStamp(){
        return LocalDateTime.now().toString();
    }

}
