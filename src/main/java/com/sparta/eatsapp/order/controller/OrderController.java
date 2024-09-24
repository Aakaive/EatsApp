package com.sparta.eatsapp.order.controller;

import com.sparta.eatsapp.auth.dto.AuthUser;
import com.sparta.eatsapp.common.annotation.Auth;
import com.sparta.eatsapp.order.dto.OrderRequestDto;
import com.sparta.eatsapp.order.dto.OrderResponseDto;
import com.sparta.eatsapp.order.dto.OrderStatusRequestDto;
import com.sparta.eatsapp.order.dto.OrderStatusResponseDto;
import com.sparta.eatsapp.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문
    @PostMapping("/order")
    public ResponseEntity<OrderResponseDto> order(@RequestBody OrderRequestDto requestDto, @Auth AuthUser authUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.order(requestDto, authUser));
    }

    // 고객의 주문 조회
    @GetMapping("/order")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@Auth AuthUser authUser) {
        return ResponseEntity.ok(orderService.getOrders(authUser));
    }

    // 해당 매장의 모든 주문 조회
    @GetMapping("/restaurant/{restaurantId}/order")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@PathVariable Long restaurantId, @Auth AuthUser authUser) {
        return ResponseEntity.ok(orderService.getOrders(restaurantId, authUser));
    }

    // 고객의 주문 취소
    @PatchMapping("/user/order/{orderId}")
    public ResponseEntity<String> cancelOrder(@Auth AuthUser authUser, @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(authUser, orderId));
    }

    // 사장의 주문 취소
    @PatchMapping("/owner/order/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId, @Auth AuthUser authUser) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId, authUser));
    }

    // 주문 상태 변경
    @PatchMapping("/orderStatus")
    public ResponseEntity<OrderStatusResponseDto> changeStatus(@RequestBody OrderStatusRequestDto requestDto, @Auth AuthUser authUser) {
        return ResponseEntity.ok(orderService.changeStatus(requestDto, authUser));
    }
}