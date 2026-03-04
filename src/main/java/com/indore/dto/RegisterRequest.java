package com.indore.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private int age;
    private String password;
}
