package com.example.hotelmanagement.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name="bookings")

public class Booking {
     @Id 
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="check in date is required")
    private LocalDate checkInDate;

    @Future(message="Check out date must be in future")
    private LocalDate checkOutDate;

    @Min(value=1, message="Num of Adults must not be less than 1")
    private int numOfAdults;

    @Min(value=0, message="Num of Adults must not be less than 0")
    private int numOfChildren;
    private int totalNumOfGuest;
    private String bookingConfirmationCode;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

    public void calculateTotalNumberOfGuest(){
        this.totalNumOfGuest=this.numOfAdults+this.numOfChildren;
    }

    public void setNumOfAdults(int numOfAdults){
        this.numOfAdults=numOfAdults;
        calculateTotalNumberOfGuest();
    }

    public void setNumOfChildren(int numOfChildren){
        this.numOfChildren=numOfChildren;
        calculateTotalNumberOfGuest();
    }

    @Override
    public String toString(){
        return "Booking{" +
                "id=" +id +
                ", checkInDate=" +checkInDate+
                ", checkOutDate=" +checkOutDate+
                ", numOfAdults=" +numOfAdults+
                ", numOfChildren=" +numOfChildren+
                ", totalNumOfGuest=" +totalNumOfGuest+
                ", bookinConfirmationCode='" +bookingConfirmationCode+'\'' +
                '}';

    }

}

// package com.example.hotelmanagement.entity;

// import jakarta.persistence.*;
// import jakarta.validation.constraints.Future;
// import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotNull;
// import lombok.Data;

// import java.time.LocalDate;

// @Data
// @Entity
// @Table(name = "bookings")
// public class Booking {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @NotNull(message = "Check-in date is required")
//     private LocalDate checkInDate;

//     @Future(message = "Check-out date must be in the future")
//     private LocalDate checkOutDate;

//     @Min(value = 1, message = "Num of Adults must not be less than 1")
//     private int numOfAdults;

//     @Min(value = 0, message = "Num of Children must not be less than 0")
//     private int numOfChildren;

//     private int totalNumOfGuest;

//     private String bookingConfirmationCode;

//     @ManyToOne(fetch = FetchType.EAGER)
//     @JoinColumn(name = "user_id")
//     private User user;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "room_id")
//     private Room room;

//     public void calculateTotalNumberOfGuest() {
//         this.totalNumOfGuest = this.numOfAdults + this.numOfChildren;
//     }

//     public void setNumOfAdults(int numOfAdults) {
//         this.numOfAdults = numOfAdults;
//         calculateTotalNumberOfGuest();
//     }

//     public void setNumOfChildren(int numOfChildren) {
//         this.numOfChildren = numOfChildren;
//         calculateTotalNumberOfGuest();
//     }

//     @Override
//     public String toString() {
//         return "Booking{" +
//                 "id=" + id +
//                 ", checkInDate=" + checkInDate +
//                 ", checkOutDate=" + checkOutDate +
//                 ", numOfAdults=" + numOfAdults +
//                 ", numOfChildren=" + numOfChildren +
//                 ", totalNumOfGuest=" + totalNumOfGuest +
//                 ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
//                 '}';
//     }
// }
