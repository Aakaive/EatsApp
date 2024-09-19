package com.sparta.eatsapp.order.entity;

import com.sparta.eatsapp.common.Timestamped;
import com.sparta.eatsapp.order.dto.OrderRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long orderId;

    @Column(name = "marketId", nullable = false, length = 20)
    private Long marketId;

    @Column(name = "menuName", nullable = false, length = 20)
    private String menuName;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "customerRequest", nullable = false, length = 50)
    private String customerRequest;

    @Column(name = "deliveryFee", length = 10)
    private int deliveryFee;

    @Column(name = "totalPrice", nullable = false, length = 10)
    private int totalPrice;

    @Column(name = "orderStatus", nullable = false, length = 15)
    OrderStatus orderStatus;

    // Menu menu

    // market market

    // User user

    public Order(OrderRequestDto requestDto) {
        this.marketId = requestDto.getMarketId();
        this.menuName = requestDto.getMenuName();
        this.price = requestDto.getPrice();
        this.number = requestDto.getNumber();
        this.customerRequest = requestDto.getCustomerRequest();
        this.deliveryFee = 2000;
        this.totalPrice = this.deliveryFee + (this.price * this.number);
        this.orderStatus = OrderStatus.REQUEST;
    }
}
