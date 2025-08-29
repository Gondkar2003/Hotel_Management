package com.example.hotelmanagement.dto;

import java.util.List;
import java.util.ArrayList;

import com.example.hotelmanagement.dto.BookingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //when dto is converted to json the null values are not shown
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String role;
    private List<BookingDTO> bookings=new ArrayList<>();
}
