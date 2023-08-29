package com.apartmentmanagement.userservice.repository;

import com.apartmentmanagement.userservice.entity.AddressEntity;
import com.apartmentmanagement.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long>{

}
