package com.sparta.legendofdelivery.domain.store.service;

import com.sparta.legendofdelivery.domain.store.dto.StoreRequestDto;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.repository.StoreRepository;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import org.springframework.stereotype.Service;


@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public DataResponse<Store> createStore (StoreRequestDto requestDto){

        Store store = new Store(requestDto);
        storeRepository.save(store);

        return new DataResponse<>(200,"가게 생성에 성공 하셨습니다.",store);

    }

}
