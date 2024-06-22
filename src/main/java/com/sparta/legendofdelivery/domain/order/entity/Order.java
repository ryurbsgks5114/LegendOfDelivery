package com.sparta.legendofdelivery.domain.order.entity;


import com.sparta.legendofdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.global.entity.Timestamped;
import com.sparta.legendofdelivery.global.exception.BadRequestException;
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

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    public Order(User user, Store store, OrderRequestDto requestDto) {
        this.user = user;
        this.store = store;
        this.count = requestDto.getCount();
        this.orderStatus = OrderStatusEnum.ACCEPTANCE;
        calculateTotalPrice();
    }

    public Order(Long storeId, Integer count) {
        super();
    }

    public void updateOrderStatus(OrderStatusEnum updateStatus) {
        this.orderStatus = updateStatus;
    }

    private void calculateTotalPrice() {
        if (this.store != null && this.count != null) {
            this.totalPrice = (long) (this.store.getCategory().getPrice() * this.count);
        } else {
            throw new BadRequestException("주문을 처리할 수 없습니다: 매장 또는 수량이 설정되지 않았습니다.");
        }
    }
}