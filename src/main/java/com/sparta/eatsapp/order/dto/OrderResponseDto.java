package com.sparta.eatsapp.order.dto;

import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {

    Long orderId;
    String customerName;
    //String marketName;
    Long marketId;
    String menuName;
    int price;
    int number;
    String customerRequest;
    int deliveryFee;
    int totalPrice;
    OrderStatus orderStatus;
    LocalDateTime orderTime;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        //this.customerName = customerName;
        this.marketId = order.getMarketId();
        this.menuName = order.getMenuName();
        this.number = order.getNumber();
        this.customerRequest = order.getCustomerRequest();
        this.deliveryFee = order.getDeliveryFee();
        this.totalPrice = order.getTotalPrice();
        this.orderStatus = order.getOrderStatus();
        this.orderTime = order.getCreatedAt();
    }
}
