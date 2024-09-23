package com.sparta.eatsapp.address.repository;

import com.sparta.eatsapp.address.entity.Address;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

  Optional<Address> findByUserId(Long userid);
}
