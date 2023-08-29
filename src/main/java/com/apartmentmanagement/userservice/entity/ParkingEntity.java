package com.apartmentmanagement.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@Table(name="Parking")
@AllArgsConstructor
@NoArgsConstructor
public class ParkingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(name="floor")
    private Long floor;
    @Column(name="name")
    private String name;
    @Column(name="location")
    private String location;

    @OneToMany(mappedBy = "parking",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MappingEntity> mappingEntityList;
}
