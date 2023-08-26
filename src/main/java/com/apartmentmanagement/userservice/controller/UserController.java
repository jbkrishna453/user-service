package com.apartmentmanagement.userservice.controller;

import com.apartmentmanagement.userservice.model.Address;
import com.apartmentmanagement.userservice.model.User;
import com.apartmentmanagement.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        System.out.println(user);
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Address> getUserById(@PathVariable Long id)
    {
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.FOUND);
    }

    

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user)
    {
        return new ResponseEntity<>(userService.updateUser(user),HttpStatus.OK);
    }
    @DeleteMapping("/users")
    public String deleteUser(@RequestParam Long id)
    {
        userService.deleteUserById(id);
        return "success";
    }
}
