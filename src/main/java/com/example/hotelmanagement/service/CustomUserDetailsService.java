// package com.example.hotelmanagement.service;

// import java.util.Collections;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
// import com.example.hotelmanagement.entity.User;

// import com.example.hotelmanagement.exception.OurException;
// import com.example.hotelmanagement.repo.UserRepository;

// @Service
// public class CustomUserDetailsService implements UserDetailsService{
//     @Autowired
//     private UserRepository userRepository;
//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         userRepository.findByEmail(username).orElseThrow(()->new OurException("Username/Email Not Found"));
//        return new org.springframework.security.core.userdetails.User(
//                 user.getEmail(),                       // username
//                 user.getPassword(),                    // encoded password
//                 Collections.singletonList(
//                         new SimpleGrantedAuthority(user.getRole()) // ROLE as authority
//                 )
//         );
//     }
// }


package com.example.hotelmanagement.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.entity.User;
import com.example.hotelmanagement.exception.OurException;
import com.example.hotelmanagement.repo.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from DB and assign to a variable
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new OurException("Username/Email Not Found"));

        // Return UserDetails object with username, password, and role
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),                       // username
                user.getPassword(),                    // encoded password
                Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole()) // ROLE as authority
                )
        );
    }
}
