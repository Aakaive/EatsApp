package com.sparta.eatsapp.order.service;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.order.dto.OrderRequestDto;
import com.sparta.eatsapp.order.dto.OrderResponseDto;
import com.sparta.eatsapp.order.dto.OrderStatusResponseDto;
import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.entity.OrderStatus;
import com.sparta.eatsapp.order.repository.OrderRepository;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.restaurant.repository.RestaurantRepository;
import com.sparta.eatsapp.user.entity.User;
import com.sparta.eatsapp.user.enums.UserRole;
import com.sparta.eatsapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    // 주문
    //@OrderStatusAop
    public OrderResponseDto order(OrderRequestDto requestDto, AuthUser authUser) {
        Restaurant restaurant = restaurantRepository.findById(requestDto.getRestaurantId()).orElseThrow(() ->
                new IllegalArgumentException("유효한 매장이 아닙니다."));

        User user = findByUserId(authUser.getId());

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

        Order order = new Order(requestDto, restaurant, user);
        Order RequestOrder = orderRepository.save(order);

        return new OrderResponseDto(RequestOrder);
    }

    // 고객의 주문 조회
    public List<OrderResponseDto> getOrders(AuthUser authUser) {
        List<Order> orderList = orderRepository.findAllByUserId(authUser.getId());
        List<OrderResponseDto> list = new ArrayList<>();

        for(Order order : orderList) {
            list.add(new OrderResponseDto(order));
        }

        return list;
    }

    // 매장의 주문 조회
    public List<OrderResponseDto> getOrders(Long restaurantId, AuthUser authUser) {
        User user = findByUserId(authUser.getId());
        if(!user.getRole().equals(UserRole.OWNER)){
            throw new IllegalArgumentException("사장만 조회가 가능합니다.");
        }

        /*

        로그인한 유저와 매장 주인이 일치하는지 확인하는 로직 필요

         */
        List<Order> orderList = orderRepository.findAllByRestaurantId(restaurantId);
        List<OrderResponseDto> list = new ArrayList<>();
        for(Order orderLists : orderList) {
            list.add(new OrderResponseDto(orderLists));
        }

        return list;
    }

    // 고객의 주문 취소
    @Transactional
    public String cancelOrder(AuthUser authUser, Long orderId) {
        Order order = findByOrderId(orderId);

        if(!(order.getUser().getId().equals(authUser.getId()))) {
            throw new IllegalArgumentException("주문자와 로그인 한 유저가 일치하지 않습니다.");
        }

        if(order.getOrderStatus() == OrderStatus.CANCEL) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        if(!(order.getOrderStatus() == OrderStatus.REQUEST)) {
            throw new IllegalArgumentException("조리가 시작된 후에는 취소가 불가능합니다.");
        }

        order.cancelOrder();

        return "주문이 취소되었습니다.";
    }

    // 사장의 주문취소
    @Transactional
    public String cancelOrder(Long orderId, AuthUser authUser) {
        User user = findByUserId(authUser.getId());

        if(!user.getRole().equals(UserRole.OWNER)){
            throw new IllegalArgumentException("사장만 취소가 가능합니다.");
        }

        Order order = findByOrderId(orderId);

        if(order.getOrderStatus() == OrderStatus.CANCEL) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        order.cancelOrder();

        return "주문이 취소되었습니다.";
    }

    // 주문 상태 다음으로 변경
    public OrderStatusResponseDto changeStatus(Long orderId, AuthUser authUser){
        Order order = findByOrderId(orderId);
        User user = findByUserId(authUser.getId());

        if(!user.getRole().equals(UserRole.OWNER)){
            throw new IllegalArgumentException("사장만 변경이 가능합니다.");
        }

        if(order.getOrderStatus() == OrderStatus.CANCEL) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        if(order.getOrderStatus() == OrderStatus.FINISH) {
            throw new IllegalArgumentException("이미 배달이 완료된 주문입니다.");
        }

        order.nextStatus();

        return new OrderStatusResponseDto(order);
    }

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("등록된 유저가 아닙니다."));
    }

    public Order findByOrderId(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("유효한 주문번호가 아닙니다."));
    }
}