package com.apartmentmanagement.userservice.repository;

import com.apartmentmanagement.userservice.entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<ParkingEntity,Long> {
}
