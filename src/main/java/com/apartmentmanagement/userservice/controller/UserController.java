package com.apartmentmanagement.userservice.controller;

import com.apartmentmanagement.userservice.model.Address;
import com.apartmentmanagement.userservice.model.User;
import com.apartmentmanagement.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@RequestMapping("/user/v1")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println(user);
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/users/add/address")
    public ResponseEntity<List<Address>> addAddress(@RequestParam("emailId") String emailId, @RequestBody Address address){
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(userService.addUserAddress(emailId,address));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Address>> getUserById(@PathVariable Long id) {

        return new ResponseEntity<>(userService.getUserAddressById(id),HttpStatus.FOUND);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user),HttpStatus.OK);
    }
    @DeleteMapping("/users")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUserById(id);
        return "success";
    }
}
