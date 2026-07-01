package com.DTO.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SignUpResponseDto {
    private String status;
    private String message;
}
