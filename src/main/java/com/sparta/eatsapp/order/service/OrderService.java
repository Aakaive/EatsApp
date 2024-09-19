package com.sparta.eatsapp.order.service;

import com.sparta.eatsapp.order.dto.OrderRequestDto;
import com.sparta.eatsapp.order.dto.OrderResponseDto;
import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponseDto order(OrderRequestDto requestDto) {
        Order order = new Order(requestDto);
        Order RequestOrder = orderRepository.save(order);

        return new OrderResponseDto(RequestOrder);
    }

    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("유효한 주문번호가 아닙니다."));

        return new OrderResponseDto(order);
    }
}