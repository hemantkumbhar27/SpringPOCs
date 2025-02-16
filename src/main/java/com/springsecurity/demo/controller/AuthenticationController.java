package com.springsecurity.demo.controller;

import com.springsecurity.demo.dto.JWTAuthenticationResponse;
import com.springsecurity.demo.dto.SignInRequest;
import com.springsecurity.demo.dto.SignUpRequest;
import com.springsecurity.demo.entity.User;
import com.springsecurity.demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User>  signUp(@RequestBody SignUpRequest signUpRequest) {
        return  ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return  ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }
}
