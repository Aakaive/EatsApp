package com.sparta.eatsapp.order.dto;

import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {

    private Long orderId;
    private String customerName;
    //String marketName;
    private Long marketId;
    private String menuName;
    private int price;
    private int number;
    private String customerRequest;
    private int deliveryFee;
    private int totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderTime;

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
