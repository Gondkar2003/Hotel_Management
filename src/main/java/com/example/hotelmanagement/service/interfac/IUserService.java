package com.example.hotelmanagement.service.interfac;

import com.example.hotelmanagement.dto.LoginRequest;
import com.example.hotelmanagement.dto.Response;
import com.example.hotelmanagement.entity.User;

public interface IUserService{
    Response register(User user);

    Response login(LoginRequest loginRequest);
    
    Response getAllUsers();
    
    Response getUserBookingHistory(String userId );

    Response deleteUser(String userId);
    
    Response getUserById(String userId);

    Response getMyInfo(String email);
}

