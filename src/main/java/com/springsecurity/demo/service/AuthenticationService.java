package com.springsecurity.demo.service;

import com.springsecurity.demo.dto.JWTAuthenticationResponse;
import com.springsecurity.demo.dto.SignInRequest;
import com.springsecurity.demo.dto.SignUpRequest;
import com.springsecurity.demo.entity.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JWTAuthenticationResponse signIn(SignInRequest signInRequest);
}
