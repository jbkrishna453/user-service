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

import java.util.*;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final IdentityService identityService;
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public User createUser(User user) {
        if(userRepository.existsByemailId(user.getEmailId()))
        {
            throw new DataAlreadyExistsException("data already exists");
        }
        else {
            UserEntity userEntity = UserEntity.builder()
                    .emailId(user.getEmailId())
                    .FirstName(user.getFirstName())
                    .LastName(user.getLastName())
                    .phoneNo(user.getPhoneNo())
                    .build();

            List<AddressEntity> addressEntity = buildAddress(user, userEntity);
            userEntity.setAddressEntity(addressEntity);
            userRepository.save(userEntity);

            // calling identity-service
            identityService.addUser(user);
            user.setId(userEntity.getId());
            user.setAddresses(addressEntity.stream().map(n -> objectMapper.convertValue(n, Address.class)).toList());
        }
        return user;
    }

    @Override
    public User updateUser(User user)
    {
        UserEntity userEntity = userRepository.findByEmailId(user.getEmailId());
        user.setId(userEntity.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmailId(user.getEmailId());
        userEntity.setPhoneNo(user.getPhoneNo());
        List<AddressEntity> addressEntity = buildAddress(user, userEntity);
        userEntity.setAddressEntity(addressEntity);
        userRepository.save(userEntity);
        return user;
    }


    @Override
    public List<Address> getUserAddressById(Long id) {
        UserEntity userEntity= userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User details were not found"));
        System.out.println("Retrieved UserEntity: " + userEntity);
        User user=objectMapper.convertValue(userEntity,User.class);
        System.out.println(user.getAddresses());
        return user.getAddresses();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<Address> addUserAddress(String emailId, Address address){
        List<Address> addresses;
        if(!userRepository.existsByemailId(emailId)){
            throw new DataNotFoundException("User details could not be fetched");
        }else{
            UserEntity userEntity = userRepository.findByEmailId(emailId);
            AddressEntity addressEntity = objectMapper.convertValue(address, AddressEntity.class);
            addressEntity.setUserEntity(userEntity);
            userEntity.getAddressEntity().add(addressEntity);
            userRepository.save(userEntity);
            addresses = userEntity.getAddressEntity().stream().map(n -> objectMapper.convertValue(n,Address.class)).toList();
        }
        return addresses;
    }

    @Override
    public String deleteUserById(Long id) {
        UserEntity userEntity=userRepository.findById(id).orElseThrow(()->new DataNotFoundException("user not found"));
        userRepository.delete(userEntity);
        return "user deleted";
    }

    public List<AddressEntity> buildAddress(User user, UserEntity userEntity){
        return
                user.getAddresses().stream().map(n -> {
                    AddressEntity addressEntity1 = objectMapper.convertValue(n, AddressEntity.class);
                    addressEntity1.setUserEntity(userEntity);
                    return addressEntity1;
                }).toList();
    }
}
