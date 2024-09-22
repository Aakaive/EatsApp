package com.sparta.eatsapp.order.service;

import com.sparta.eatsapp.common.OrderStatusAop;
import com.sparta.eatsapp.order.dto.OrderRequestDto;
import com.sparta.eatsapp.order.dto.OrderResponseDto;
import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.entity.OrderStatus;
import com.sparta.eatsapp.order.repository.OrderRepository;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    // 주문
    //@OrderStatusAop
    public OrderResponseDto order(OrderRequestDto requestDto) {
        Restaurant restaurant = restaurantRepository.findById(requestDto.getRestaurantId()).orElseThrow(() ->
                new IllegalArgumentException("유효한 매장이 아닙니다."));

        // 오픈 시간 확인
        if(!restaurant.getOpeningTime().isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("매장 오픈 전입니다.");
        }

        // 마감 시간 확인
        if(restaurant.getClosingTime().isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("매장 마감시간입니다.");
        }

        // 최소 주문 가격 조건 확인
        if(requestDto.getPrice() * requestDto.getNumber() < restaurant.getMinimumPrice()) {
            throw new IllegalArgumentException("최소 주문 가격보다 적습니다.");
        }

        Order order = new Order(requestDto, restaurant);
        Order RequestOrder = orderRepository.save(order);

        return new OrderResponseDto(RequestOrder);
    }

    // 주문 조회
    public OrderResponseDto getOrder(Long orderId) {
        Order order = findByOrderId(orderId);

        return new OrderResponseDto(order);
    }

    // 주문 취소
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