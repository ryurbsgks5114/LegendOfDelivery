package com.sparta.legendofdelivery.domain.order.repository;

import com.sparta.legendofdelivery.domain.order.entity.Order;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order, Long > {
  int countByUserAndStore(User user, Store store);
  Page<Order> findAllByUser(User user, Pageable pageable);
}
