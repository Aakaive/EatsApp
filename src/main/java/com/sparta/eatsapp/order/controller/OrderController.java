package com.sparta.eatsapp.order.controller;

import com.sparta.eatsapp.order.dto.OrderRequestDto;
import com.sparta.eatsapp.order.dto.OrderResponseDto;
import com.sparta.eatsapp.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문
    @PostMapping("/order")
    public ResponseEntity<OrderResponseDto> order(@RequestBody OrderRequestDto requestDto) {
        //return ResponseEntity.ok(orderService.order(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.order(requestDto));
    }
}