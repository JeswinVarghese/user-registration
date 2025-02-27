package com.example.userregistration.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserRegistrationRequestDto {

	@NotBlank
    private String name;
    
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String gender;
    
    @NotBlank
    private String password;
}
