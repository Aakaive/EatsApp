package com.sparta.eatsapp.order.repository;

import com.sparta.eatsapp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserId(Long userId);

    List<Order> findAllByRestaurantId(Long id);

    List<Order> findAllByUserId(Long id);
}