package com.sparta.eatsapp.review.entity;

import com.sparta.eatsapp.common.Timestamped;
import com.sparta.eatsapp.order.entity.Order;
import com.sparta.eatsapp.review.dto.ReviewRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review")
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long reviewId;

    @Column(name = "restaurantId", nullable = false, length = 20)
    private Long restaurantId;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Min(value = 1)
    @Max(value = 5)
    @Column(name = "star", nullable = false)
    private int star;

    @OneToOne(mappedBy = "review")
    private Order order;

    public Review(ReviewRequestDto requestDto, Order order) {
        this.order = order;
        this.restaurantId = order.getRestaurant().getId();
        this.content = requestDto.getContent();
        this.star = requestDto.getStar();
    }

    public void modifiedReview(ReviewRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.star = requestDto.getStar();
    }
}