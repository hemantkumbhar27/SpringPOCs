package com.springsecurity.demo.config;

import com.springsecurity.demo.service.JWTService;
import com.springsecurity.demo.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

       if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
           filterChain.doFilter(request, response);
           return;
       }

       jwtToken = authorizationHeader.substring(7);
       userEmail= jwtService.extractUsername(jwtToken) ;

       if(StringUtils.hasText(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

           if(jwtService.isValidateToken(jwtToken, userDetails)) {
               SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

               UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                       userDetails, null, userDetails.getAuthorities()
               );

               token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               securityContext.setAuthentication(token);
               SecurityContextHolder.setContext(securityContext);
           }
       }
        filterChain.doFilter(request, response);
    }
}
