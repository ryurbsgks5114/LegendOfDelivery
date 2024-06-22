package com.sparta.legendofdelivery.domain.dibs.repository;

import com.sparta.legendofdelivery.domain.dibs.entity.Dibs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DibsRepository extends JpaRepository<Dibs, Long> {
    Optional<Dibs> findByStoreIdAndUserId(long storeId, long userId);
    List<Dibs> findAllByUserId(long userId);
}
