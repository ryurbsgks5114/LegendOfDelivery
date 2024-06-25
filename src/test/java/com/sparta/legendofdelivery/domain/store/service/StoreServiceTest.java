package com.sparta.legendofdelivery.domain.store.service;

import com.sparta.legendofdelivery.domain.store.dto.StoreRequestDto;
import com.sparta.legendofdelivery.domain.store.entity.Category;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.repository.StoreRepository;
import com.sparta.legendofdelivery.domain.store.service.StoreService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class) // @Mock 사용을 위해 설정합니다.
public class StoreServiceTest {

//    @Mock
//    StoreRepository storeRepository;
//
//    @InjectMocks
//    StoreService storeService;
//
//    @Test
//    @DisplayName("createStore 성공 시 로직")
//    public void createStore() {
//
//        //given
//        StoreRequestDto requestDto = new StoreRequestDto();
//        requestDto.setIntro("fdafd");
//        requestDto.setCategory(Category.KOREAN);
//        requestDto.setName("name");
//        Store store = new Store(requestDto);
//        new DataResponse<Store>(200, "가게 생성에 성공 하셨습니다.", store);
//        //when
//
//        storeRepository.save(store);
//
//        //then
//
//        assertNotNull(store);
//        assertNotNull(requestDto.getIntro());
//        assertNotNull(requestDto.getCategory());
//        assertNotNull(requestDto.getName());
//
//    }
//
//    @Test
//    @DisplayName("createStore 카테고리 값 없을때 로직")
//    public void createStoreNonCategory() {
//
//        //given
//        StoreRequestDto requestDto = new StoreRequestDto();
//        requestDto.setIntro("fdafd");
//        requestDto.setName("name");
//        Store store = new Store(requestDto);
//        new DataResponse<Store>(200, "가게 생성에 성공 하셨습니다.", store);
//        //when
//
//        storeRepository.save(store);
//
//        //then
//
//        assertNotNull(store);
//        assertNotNull(requestDto.getIntro());
//        assertNull(requestDto.getCategory());
//        assertNotNull(requestDto.getName());
//
//    }
//
//    @Test
//    @DisplayName("createStore 가게 이름 없을 시 로직")
//    public void createStoreNonName() {
//
//        //given
//        StoreRequestDto requestDto = new StoreRequestDto();
//        requestDto.setIntro("fdafd");
//        requestDto.setCategory(Category.KOREAN);
//        Store store = new Store(requestDto);
//        new DataResponse<Store>(200, "가게 생성에 성공 하셨습니다.", store);
//        //when
//
//        storeRepository.save(store);
//
//        //then
//
//        assertNotNull(store);
//        assertNotNull(requestDto.getIntro());
//        assertNotNull(requestDto.getCategory());
//        assertNull(requestDto.getName());
//
//    }
}
