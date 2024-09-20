package com.sparta.eatsapp.password.repository;

import com.sparta.eatsapp.password.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password,Long> {

}
