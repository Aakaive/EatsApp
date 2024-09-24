package com.sparta.eatsapp.config;

import com.sparta.eatsapp.order.dto.OrderRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j(topic = "AOP 로직")
@Aspect
@Component
public class AspectConfig {

    @Pointcut("@annotation(com.sparta.eatsapp.common.annotation.OrderStatusAop)")
    private void orderStatus() {}

    @Around("orderStatus()")
    public Object orderStatusChange(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info(":::::: AOP 실행 ::::::");

        try {
            joinPoint.proceed();
            return joinPoint;
        } finally {
            // 가게 id, 주문 id, 요청 시각
            log.info("요청 시각 : {}", LocalDateTime.now());
            Object[] list = joinPoint.getArgs();
            OrderRequestDto dto = (OrderRequestDto) list[0];

            log.info("가게 id : {}", dto.getRestaurantId());
        }
    }

}
