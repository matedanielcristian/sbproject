package com.internship.sbproject1.repository;

import com.internship.sbproject1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("Select s from User s where s.email = ?1")
    Optional<User> findUserByEmail(String email);
//    void deleteRelation(@Param("user_id") Long user_id);
}