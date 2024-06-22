package com.sparta.legendofdelivery.domain.dibs.repository;

import com.sparta.legendofdelivery.domain.dibs.entity.Dibs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DibsRepository extends JpaRepository<Dibs, Long> {

}
