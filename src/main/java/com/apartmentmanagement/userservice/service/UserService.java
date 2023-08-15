package com.apartmentmanagement.userservice.service;

import com.apartmentmanagement.userservice.model.Address;
import com.apartmentmanagement.userservice.model.User;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    Address getUserById(Long id);
    String deleteUserById(Long id);
}
