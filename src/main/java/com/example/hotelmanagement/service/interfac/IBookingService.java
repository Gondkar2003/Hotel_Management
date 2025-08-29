package com.example.hotelmanagement.service.interfac;

import com.example.hotelmanagement.dto.Response;
import com.example.hotelmanagement.entity.Booking;

public interface IBookingService {
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response  findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
