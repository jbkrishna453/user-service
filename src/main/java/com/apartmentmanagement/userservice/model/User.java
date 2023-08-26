package com.apartmentmanagement.userservice.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@ToString
public class User implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNo;
    private Address address;
    private String password;
    private Recovery recovery;
    private List<Parking> parkingList;
}
