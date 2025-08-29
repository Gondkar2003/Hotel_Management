package com.example.hotelmanagement.service.interfac;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.hotelmanagement.dto.Response;
import com.example.hotelmanagement.dto.RoomDTO;

public interface IRoomService {
    // Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);
    Response addNewRoom(RoomDTO roomDTO);

    List<String> getAllRoomTypes();

    Response getAllRooms();

    Response deleteRoom(Long roomId);

    Response updateRoom(Long roomId,String description, String roomType, BigDecimal roomPrice, MultipartFile photo);

    Response getRoomById(Long roomId);

    Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOuDate, String roomType);

    Response getAllAvailableRooms();

}
