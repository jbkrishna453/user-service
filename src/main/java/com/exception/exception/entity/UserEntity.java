package com.exception.exception.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String FirstName;
    private String LastName;
    private String emailId;
    private String phoneNo;

    @OneToOne(mappedBy = "userEntity",cascade = CascadeType.ALL,orphanRemoval = true)
    private AddressEntity addressEntity;
}
