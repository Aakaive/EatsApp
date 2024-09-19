package com.sparta.eatsapp.order.repository;

import com.sparta.eatsapp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}