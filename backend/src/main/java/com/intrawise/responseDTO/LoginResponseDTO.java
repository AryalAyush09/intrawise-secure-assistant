package com.intrawise.responseDTO;

import com.intrawise.Enum.Role;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponseDTO {
   private String token;
   private Role role;
   private String fullName;
}
