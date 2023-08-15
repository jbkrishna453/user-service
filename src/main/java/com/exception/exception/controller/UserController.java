package com.exception.exception.controller;

import com.exception.exception.entity.UserEntity;
import com.exception.exception.model.Address;
import com.exception.exception.model.User;
import com.exception.exception.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
