package com.apartmentmanagement.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdentityAddUserRequest implements Serializable {

    private String userName;
    private String emailId;
    private String password;
    private Recovery recovery;
}
