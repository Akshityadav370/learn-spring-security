package com.learn_spring_security.spring_security.dto;


import com.learn_spring_security.spring_security.entity.enums.Permission;
import com.learn_spring_security.spring_security.entity.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignupDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
