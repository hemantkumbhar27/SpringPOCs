package com.springsecurity.demo.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String fname;
    private String lname;
    private String email;
    private String password;

}
