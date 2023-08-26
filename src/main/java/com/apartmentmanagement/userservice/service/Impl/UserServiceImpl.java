package com.apartmentmanagement.userservice.service.Impl;

import com.apartmentmanagement.userservice.entity.*;
import com.apartmentmanagement.userservice.repository.ParkingRepository;
import com.apartmentmanagement.userservice.service.IdentityService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;
    private final ObjectMapper objectMapper;
    private final IdentityService identityService;
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public User createUser(User user) {
        AddressEntity addressEntity=objectMapper.convertValue(user.getAddress(),AddressEntity.class);
        List<ParkingEntity> parkingEntityList=user.getParkingList().stream()
                .map(parking -> objectMapper.convertValue(parking,ParkingEntity.class)).toList();
        parkingEntityList.forEach(parkingEntity -> parkingRepository.save(parkingEntity));

        if(userRepository.existsByemailId(user.getEmailId()))
        {
            throw new DataAlreadyExistsException("data already exists");
        }
        else {
            UserEntity userEntity = UserEntity.builder()
                    .emailId(user.getEmailId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .addressEntity(addressEntity)
                    .phoneNo(user.getPhoneNo())
                    .mappingEntityList(new ArrayList<>())
                    .build();
            List<MappingEntity> mappingEntityList=parkingEntityList.stream()
                            .map(parkingEntity -> {
                                MappingEntityId mappingEntityId=new MappingEntityId(userEntity.getId(),parkingEntity.getId());
                                return MappingEntity.builder()
                                        .id(mappingEntityId)
                                        .userEntity(userEntity)
                                        .parking(parkingEntity)
                                        .build();
                            })
                                    .collect(Collectors.toList());
            userEntity.setMappingEntityList(mappingEntityList);

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
        return Address.builder()
                .street(userEntity.getAddressEntity().getStreet())
                .city(userEntity.getAddressEntity().getCity())
                .country(userEntity.getAddressEntity().getCountry())
                .build();
    }

    @Override
    public String deleteUserById(Long id) {
        UserEntity userEntity=userRepository.findById(id).orElseThrow(()->new DataNotFoundException("user not found"));
        userRepository.delete(userEntity);
        return "user deleted";
    }
}
