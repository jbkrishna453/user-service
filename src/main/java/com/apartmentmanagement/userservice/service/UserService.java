package com.apartmentmanagement.userservice.service;

import com.apartmentmanagement.userservice.model.Address;
import com.apartmentmanagement.userservice.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    List<Address> getUserAddressById(Long id);
    String deleteUserById(Long id);
}
