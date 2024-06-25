package com.sparta.legendofdelivery.domain.dibs.service;

import com.sparta.legendofdelivery.domain.review.util.TestUtil;
import com.sparta.legendofdelivery.domain.store.entity.Category;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.repository.StoreRepository;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.entity.UserOauth;
import com.sparta.legendofdelivery.domain.user.entity.UserRole;
import com.sparta.legendofdelivery.domain.user.entity.UserStatus;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DibsServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private DibsService service;

    User user = new User();
    Store store = new Store();

    @BeforeEach
    void setUp() throws Exception {
        TestUtil.setField(user, "id", 1L);
        TestUtil.setField(user, "userId", "test1");
        TestUtil.setField(user, "password", "test1234!");
        TestUtil.setField(user, "name", "이름1");
        TestUtil.setField(user, "role", UserRole.USER);
        TestUtil.setField(user, "status", UserStatus.NORMAL);
        TestUtil.setField(user, "oauth", UserOauth.OUR);

        TestUtil.setField(store, "id", 1L);
        TestUtil.setField(store, "name", "스토어가게");
        TestUtil.setField(store, "category", Category.CHINA);
        TestUtil.setField(store, "intro", "안녕하세요.");
        TestUtil.setField(store, "dibsCount", 0L);

        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));
    }

    @Test
    @DisplayName("찜 등록 성공")
    void addDibsSuccess() throws Exception {

        // Call the actual service method
        MessageResponse response = service.addDibs(store.getId(), user);

        // Assertions remain the same
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("가게 찜에 성공했습니다.", response.getMessage());
    }

}