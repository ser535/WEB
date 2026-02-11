package com.example.demo.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDTO  {

    @NotBlank(message = "Login must not be blank")
    @Size(min = 1, max = 30, message = "Login length must be 1..30")
    private String login;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 1, max = 64, message = "Password length must be 1..64")
    private String password;
}
