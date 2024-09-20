package com.sparta.eatsapp.user.repository;

import com.sparta.eatsapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
}