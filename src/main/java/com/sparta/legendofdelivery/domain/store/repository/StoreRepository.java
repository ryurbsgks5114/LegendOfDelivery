package com.sparta.legendofdelivery.domain.store.repository;

import com.sparta.legendofdelivery.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findAllByOrderByCreateAtDesc(Pageable pageable);
}
