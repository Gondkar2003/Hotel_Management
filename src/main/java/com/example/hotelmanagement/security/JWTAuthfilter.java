package com.example.hotelmanagement.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.hotelmanagement.service.CustomUserDetailsService;
import com.example.hotelmanagement.utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthfilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;

    // @Autowired
    // private CachingUserDetailsService cachingUserDetailsService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // ✅ use this instead

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if(authHeader==null || authHeader.isBlank()){
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken=authHeader.substring(7);
        userEmail=jwtUtils.extractUsername(jwtToken);
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null){  //It first checks if an email exists and no one is already authenticated.
            // UserDetails userDetails=cachingUserDetailsService.loadUserByUsername(userEmail);  //If so, it loads the user details from DB/cache using the email.
            UserDetails userDetails=customUserDetailsService.loadUserByUsername(userEmail);
            if(jwtUtils.isValidToken(jwtToken, userDetails)){                                 //Then it validates the JWT token against those user details.
                SecurityContext securityContext=SecurityContextHolder.createEmptyContext();   //If valid, it creates a fresh empty SecurityContext.
                UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());//Builds a UsernamePasswordAuthenticationToken (Spring’s way of representing a logged-in user).
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //Attaches request details (like IP, session) to that token.
                securityContext.setAuthentication(token);                                     //Finally, sets this authentication inside SecurityContextHolder, meaning the user is now considered logged in for this request.
                SecurityContextHolder.setContext(securityContext);

            }
        }
        filterChain.doFilter(request, response);
    }
}