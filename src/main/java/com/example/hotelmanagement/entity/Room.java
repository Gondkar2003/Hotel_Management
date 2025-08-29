package com.example.hotelmanagement.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="rooms")
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings=new ArrayList<>();

    @Override
    public String toString(){
        return "Room{" +
        "id=" +id+
        ",roomType='" +roomType+ '\'' +
        ",roomPrice='" +roomPrice+ 
        ",roomPhotoUrl='" +roomPhotoUrl+ '\'' +
        ",roomDescription='" +roomDescription+ '\'' +
        '}';
    }

}


// package com.example.hotelmanagement.entity;

// import jakarta.persistence.*;
// import lombok.Data;

// import java.math.BigDecimal;
// import java.util.ArrayList;
// import java.util.List;

// @Data
// @Entity
// @Table(name = "rooms")
// public class Room {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String roomType;
//     private BigDecimal roomPrice;
//     private String roomPhotoUrl;
//     private String roomDescription;

//     @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
//     private List<Booking> bookings = new ArrayList<>();

//     @Override
//     public String toString() {
//         return "Room{" +
//                 "id=" + id +
//                 ", roomType='" + roomType + '\'' +
//                 ", roomPrice=" + roomPrice +
//                 ", roomPhotoUrl='" + roomPhotoUrl + '\'' +
//                 ", roomDescription='" + roomDescription + '\'' +
//                 '}';
//     }
// }
