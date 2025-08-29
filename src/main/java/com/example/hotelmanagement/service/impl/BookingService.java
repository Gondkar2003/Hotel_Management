package com.example.hotelmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.hotelmanagement.dto.BookingDTO;
import com.example.hotelmanagement.dto.Response;
import com.example.hotelmanagement.entity.Booking;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.entity.User;
import com.example.hotelmanagement.exception.OurException;
import com.example.hotelmanagement.repo.BookingRepository;
import com.example.hotelmanagement.repo.RoomRepository;
import com.example.hotelmanagement.repo.UserRepository;
import com.example.hotelmanagement.service.interfac.IBookingService;
import com.example.hotelmanagement.service.interfac.IRoomService;
import com.example.hotelmanagement.utils.Utils;

@Service
public class BookingService implements IBookingService{

    @Autowired
    BookingRepository bookingRepository;

    @Autowired 
    private IRoomService RoomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response=new Response();
        try{
                if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
                    throw new IllegalArgumentException("Check in date must come after check out date");
                }
                Room room=roomRepository.findById(roomId).orElseThrow(()->new OurException("Room not found"));
                User user=userRepository.findById(userId).orElseThrow(()->new OurException("User not found"));

                List<Booking> existingBookings=room.getBookings();
                if(!roomIsAvailable(bookingRequest, existingBookings)){
                    throw new OurException("Room not available for selected range");
                }

                bookingRequest.setRoom(room);
                bookingRequest.setUser(user);
                String bookingConfirmationCode=Utils.generateRandomAlphanumeric(10);
                bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
                bookingRepository.save(bookingRequest);
                response.setStatusCode(200);
                response.setMessage("successful");
                response.setBookingConfirmationCode(bookingConfirmationCode);

        }catch(OurException e){
                response.setStatusCode(404);
                response.setMessage(e.getMessage());

        }catch(Exception e){
            response.setStatusCode(500);
                response.setMessage("Error saving a booking" +e.getMessage());
        }
        return response;
    }


    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response=new Response();
        try{
                Booking booking=bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(()->new OurException("Booking not found"));
                BookingDTO bookingDTO=Utils.mapBookingEntityToBookingDTO(booking);
                response.setStatusCode(200);
                response.setMessage("successful");
                response.setBooking(bookingDTO);

        }catch(OurException e){
                response.setStatusCode(404);
                response.setMessage(e.getMessage());

        }catch(Exception e){
            response.setStatusCode(500);
                response.setMessage("Error finding a booking" +e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response=new Response();
        try{
                List<Booking> bookingList=bookingRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
                List<BookingDTO> bookingDTOList=Utils.mapBookingListEntityToBookingListDTO(bookingList);
                response.setStatusCode(200);
                response.setMessage("successful");
                response.setBookingList(bookingDTOList);

        }catch(OurException e){
                response.setStatusCode(404);
                response.setMessage(e.getMessage());

        }catch(Exception e){
            response.setStatusCode(500);
                response.setMessage("Error getting a booking" +e.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response=new Response();
        try{
                Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new OurException("Booking does not exists"));
                bookingRepository.deleteById(bookingId);
                response.setStatusCode(200);
                response.setMessage("successful");

        }catch(OurException e){
                response.setStatusCode(404);
                response.setMessage(e.getMessage());

        }catch(Exception e){
            response.setStatusCode(500);
                response.setMessage("Error cancelling a booking" +e.getMessage());
        }
        return response;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {

    return existingBookings.stream()
        .noneMatch(existingBooking ->
            bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
            || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
            || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
            || (bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate())
                && bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate()))
            || bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate())
            || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
            || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
            || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
        );
}

}
