package com.sparta.legendofdelivery.domain.dibs.service;

import com.sparta.legendofdelivery.domain.dibs.repository.DibsRepository;
import com.sparta.legendofdelivery.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;

@Service
public class DibsService {

    private final DibsRepository dibsRepository;
    private final StoreRepository storeRepository;

    public DibsService(DibsRepository dibsRepository, StoreRepository storeRepository) {
        this.dibsRepository = dibsRepository;
        this.storeRepository = storeRepository;
    }
}
