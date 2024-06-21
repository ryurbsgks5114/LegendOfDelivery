package com.sparta.legendofdelivery.domain.order.entity;


import com.sparta.legendofdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatusEnum orderStatus;

    @Column(nullable = false)
    private Integer count;

//    @Column(name = "total_price", nullable = false)
//    private Long totalPrice;

    public Order(User user, Store store, OrderRequestDto requestDto) {
        this.user = user;
        this.count = requestDto.getCount();
        this.store = store;
        this.orderStatus = OrderStatusEnum.ACCEPTANCE;
    }

    public void updateOrderStatus(OrderStatusEnum updateStatus) {
        this.orderStatus = updateStatus;
    }
}