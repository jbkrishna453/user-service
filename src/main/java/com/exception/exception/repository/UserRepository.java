package com.exception.exception.repository;

import com.exception.exception.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    boolean existsByemailId(String emailId);


    UserEntity findByEmailId(String emailId);
}
