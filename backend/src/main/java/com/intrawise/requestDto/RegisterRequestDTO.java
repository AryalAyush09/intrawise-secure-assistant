package com.intrawise.requestDto;

import com.intrawise.Enum.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO {
    private String fullName;
    private String password;
    private Role role;
    private String email;
}
