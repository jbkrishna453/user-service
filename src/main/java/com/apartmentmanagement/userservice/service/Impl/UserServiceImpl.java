package com.apartmentmanagement.userservice.service.Impl;

import com.apartmentmanagement.userservice.entity.AddressEntity;
import com.apartmentmanagement.userservice.service.IdentityService;
import com.apartmentmanagement.userservice.entity.UserEntity;
import com.apartmentmanagement.userservice.exception.DataAlreadyExistsException;
import com.apartmentmanagement.userservice.exception.DataNotFoundException;
import com.apartmentmanagement.userservice.model.Address;
import com.apartmentmanagement.userservice.model.User;
import com.apartmentmanagement.userservice.repository.UserRepository;
import com.apartmentmanagement.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final IdentityService identityService;
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public User createUser(User user) {
        AddressEntity addressEntity=objectMapper.convertValue(user.getAddress(),AddressEntity.class);
        if(userRepository.existsByemailId(user.getEmailId()))
        {
            throw new DataAlreadyExistsException("data already exists");
        }
        else {
            UserEntity userEntity = UserEntity.builder()
                    .emailId(user.getEmailId())
                    .FirstName(user.getFirstName())
                    .LastName(user.getLastName())
                    .addressEntity(addressEntity)
                    .phoneNo(user.getPhoneNo())
                    .build();

            addressEntity.setUserEntity(userEntity);
            Address address = objectMapper.convertValue(userEntity.getAddressEntity(), Address.class);
            userRepository.save(userEntity);
            identityService.addUser(user);
            user.setId(userEntity.getId());
            user.setAddress(address);
        }
        return user;
    }

    @Override
    public User updateUser(User user)
    {
        UserEntity userEntity=userRepository.findByEmailId(user.getEmailId());
        user.setId(userEntity.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmailId(user.getEmailId());
        userEntity.setPhoneNo(user.getPhoneNo());
        AddressEntity addressEntity=AddressEntity.builder()
                        .street(user.getAddress().getStreet())
                        .city(user.getAddress().getCity())
                        .country(user.getAddress().getCountry()).build();
        userEntity.setAddressEntity(addressEntity);
        userRepository.save(userEntity);
        return user;
    }


    @Override
    public Address getUserById(Long id) {
        UserEntity userEntity= userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("user not found"));
        System.out.println("Retrieved UserEntity: " + userEntity);
        User user=objectMapper.convertValue(userEntity,User.class);
        System.out.println(user.getAddress());
        return user.getAddress();
    }

    @Override
    public String deleteUserById(Long id) {
        UserEntity userEntity=userRepository.findById(id).orElseThrow(()->new DataNotFoundException("user not found"));
        userRepository.delete(userEntity);
        return "user deleted";
    }
}
