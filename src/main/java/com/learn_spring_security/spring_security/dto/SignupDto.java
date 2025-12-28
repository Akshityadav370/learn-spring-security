package com.learn_spring_security.spring_security.dto;


import lombok.Data;

@Data
public class SignupDto {
    private String email;
    private String password;
    private String name;
}
