package com.sparta.eatsapp.order.entity;

import com.sparta.eatsapp.common.Timestamped;
import com.sparta.eatsapp.order.dto.OrderRequestDto;
import com.sparta.eatsapp.restaurant.entity.Restaurant;
import com.sparta.eatsapp.user.entity.User;
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

//    @Column(name = "restaurantId", nullable = false, length = 20)
//    private Long restaurantId;

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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "orderStatus", nullable = false, length = 15)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menuId")
//    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantId")
    private Restaurant restaurant;



    public Order(OrderRequestDto requestDto, Restaurant restaurant) {
        this.restaurant = restaurant;
        this.menuName = requestDto.getMenuName();
        this.price = requestDto.getPrice();
        this.number = requestDto.getNumber();
        this.customerRequest = requestDto.getCustomerRequest();
        this.deliveryFee = 2000;
        this.totalPrice = this.deliveryFee + (this.price * this.number);
        this.orderStatus = OrderStatus.REQUEST;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
    }
}
