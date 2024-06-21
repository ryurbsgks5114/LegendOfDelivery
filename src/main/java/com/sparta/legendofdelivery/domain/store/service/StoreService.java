package com.sparta.legendofdelivery.domain.store.service;

import com.sparta.legendofdelivery.domain.store.dto.StoreRequestDto;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.entity.StoreState;
import com.sparta.legendofdelivery.domain.store.repository.StoreRepository;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import com.sparta.legendofdelivery.global.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public DataResponse<Store> createStore(StoreRequestDto requestDto) {

        Store store = new Store(requestDto);
        storeRepository.save(store);

        return new DataResponse<>(200, "가게 생성에 성공 하셨습니다.", store);

    }

    public DataResponse<Store> getStoreById(Long id) {

        return new DataResponse<>(200, "가게 조회에 성공 하셨습니다.", findStoreById(id));

    }

    @Transactional
    public DataResponse<Store> updateStore(Long id, StoreRequestDto requestDto) {

        Store store = findStoreById(id);
        store.updateStore(requestDto);

        return new DataResponse<>(200, "가게 수정에 성공 하셨습니다.", store);

    }

    public MessageResponse deleteStore(Long id) {

        findStoreById(id);
        storeRepository.deleteById(id);

        return new MessageResponse(200, "가게 삭제에 성공 하셨습니다");

    }

    public Page<Store> findAllStores(int page) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Store> storePage = storeRepository.findAll(pageable);

        if (!storePage.hasContent()) {
            throw new BadRequestException("가게 데이터가 비어있습니다.");
        }

        if (page >= storePage.getTotalPages()) {
            throw new BadRequestException("토탈 페이지를 넘기는 요청은 안됩니다.");
        }

        return storeRepository.findAllByOrderByCreateAtDesc(pageable);

    }

    @Transactional
    public MessageResponse openStore(Long id) {

        Store store = findStoreById(id);

        if(store.getStoreState().equals(StoreState.OPEN)){
            throw new BadRequestException("이미 오픈된 가게 입니다.");
        }

        store.openStore();

        return new MessageResponse(200, store.getName() + " 이(가) 오픈 되었습니다.");

    }

    @Transactional
    public MessageResponse closeStore(Long id) {

        Store store = findStoreById(id);

        if(store.getStoreState().equals(StoreState.CLOSE)){
            throw new BadRequestException("이미 마감된 가게 입니다.");
        }

        store.closeStore();

        return new MessageResponse(200, store.getName() + " 이(가) 마감 되었습니다.");

    }

    private Store findStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("해당 가게는 존재하지 않습니다."));
    }

}
