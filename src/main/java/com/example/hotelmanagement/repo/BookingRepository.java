package com.example.hotelmanagement.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelmanagement.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{
    List<Booking> findByRoomId(Long roomId);

    Optional<Booking> findByBookingConfirmationCode(String  code);

    List<Booking> findByUserId(Long userId);
    
}
