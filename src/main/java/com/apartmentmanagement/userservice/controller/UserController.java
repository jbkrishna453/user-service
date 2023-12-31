package com.apartmentmanagement.userservice.controller;

import com.apartmentmanagement.userservice.model.Address;
import com.apartmentmanagement.userservice.model.User;
import com.apartmentmanagement.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println(user);
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
//    public ResponseEntity<Address> getUserById(@PathVariable Long id)
//    {
//        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.FOUND);
//=======
    public ResponseEntity<List<Address>> getUserById(@PathVariable Long id) {

        return new ResponseEntity<>(userService.getUserAddressById(id),HttpStatus.FOUND);
//>>>>>>> 7143ebfa10c400fefddadf9dcb34b3c822980ddf
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
