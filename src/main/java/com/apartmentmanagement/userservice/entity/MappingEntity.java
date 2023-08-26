package com.apartmentmanagement.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_parking_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MappingEntity{

    @EmbeddedId
    private MappingEntityId id;

    @MapsId("user_id")
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",insertable = false, updatable = false)
    private UserEntity userEntity;

    @MapsId("mapped_parking_id")
    @ManyToOne
    @JoinColumn(name = "mapped_parking_id",referencedColumnName = "id",insertable = false, updatable = false)
    private ParkingEntity parking;







    // Other attributes and methods
}

