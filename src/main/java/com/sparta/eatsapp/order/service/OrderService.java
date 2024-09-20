package com.sparta.eatsapp.order.service;

import com.sparta.eatsapp.order.dto.OrderRequestDto;
import com.sparta.eatsapp.order.dto.OrderResponseDto;
import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.entity.OrderStatus;
import com.sparta.eatsapp.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Order order = findByOrderId(orderId);

        return new OrderResponseDto(order);
    }

    @Transactional
    public String cancelOrder(Long orderId) {
        Order order = findByOrderId(orderId);

        if(order.getOrderStatus() == OrderStatus.CANCEL) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        order.cancelOrder();

        return "주문이 취소되었습니다.";
    }

    public Order findByOrderId(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("유효한 주문번호가 아닙니다."));
    }
}