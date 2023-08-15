package com.exception.exception.service;

import com.exception.exception.model.Address;
import com.exception.exception.model.User;
import lombok.ToString;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    Address getUserById(Long id);
    String deleteUserById(Long id);
}
