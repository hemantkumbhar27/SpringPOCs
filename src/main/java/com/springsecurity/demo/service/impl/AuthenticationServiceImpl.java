package com.springsecurity.demo.service.impl;

import com.springsecurity.demo.dto.JWTAuthenticationResponse;
import com.springsecurity.demo.dto.SignInRequest;
import com.springsecurity.demo.dto.SignUpRequest;
import com.springsecurity.demo.entity.Role;
import com.springsecurity.demo.entity.User;
import com.springsecurity.demo.repository.UserRepository;
import com.springsecurity.demo.service.AuthenticationService;
import com.springsecurity.demo.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User signUp(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFname(signUpRequest.getFname());
        user.setLname(signUpRequest.getLname());
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public JWTAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword())
        );

        var user =  userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Email not found"));

        var jwt=jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }
}
