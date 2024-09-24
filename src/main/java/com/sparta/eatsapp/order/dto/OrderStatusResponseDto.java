package com.sparta.eatsapp.order.dto;

import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusResponseDto {
    private Long orderId;
    private OrderStatus orderStatus;

    public OrderStatusResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.orderStatus = order.getOrderStatus();
    }
}
