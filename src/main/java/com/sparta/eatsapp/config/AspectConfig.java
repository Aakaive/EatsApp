package com.sparta.eatsapp.config;

import com.sparta.eatsapp.order.dto.OrderRequestDto;
import com.sparta.eatsapp.order.dto.OrderResponseDto;
import com.sparta.eatsapp.order.dto.OrderStatusRequestDto;
import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "AOP 로직")
@Aspect
@Component
public class AspectConfig {

    private final OrderRepository orderRepository;

    public AspectConfig(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Pointcut("@annotation(com.sparta.eatsapp.common.annotation.OrderStatusAop)")
    private void orderStatus() {}

    @Around("orderStatus()")
    public Object orderStatusChange(ProceedingJoinPoint joinPoint) throws Throwable {
        Map<String, Object> param = new HashMap<>();

        try {
            Object[] list = joinPoint.getArgs();
            Object result = joinPoint.proceed();

            // 주문 시
            if(list[0] instanceof OrderRequestDto) {
                OrderRequestDto requestDto = (OrderRequestDto) list[0];
                param.put("restaurantId", requestDto.getRestaurantId());

                OrderResponseDto responseDto = (OrderResponseDto) result;
                param.put("orderId", responseDto.getOrderId());
            }

            // 주문 상태 변경 시
            if(list[0] instanceof OrderStatusRequestDto) {
                OrderStatusRequestDto requestDto = (OrderStatusRequestDto) list[0];
                param.put("orderId", requestDto.getOrderId());

                Order order = orderRepository.findById(requestDto.getOrderId()).orElseThrow(() ->
                        new IllegalArgumentException("AOP, 없는 주문번호"));

                param.put("restaurantId", order.getRestaurant().getId());
            }

            // 가게 id, 주문 id, 요청 시각
            log.info("요청 시각 : {}", LocalDateTime.now());
            log.info("가게 id : {}", param.get("restaurantId"));
            log.info("주문 id : {}", param.get("orderId"));

            return result;

        } finally { }
    }

}
