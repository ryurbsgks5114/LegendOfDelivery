package com.sparta.legendofdelivery.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.legendofdelivery.MockSpringSecurityFilter;
import com.sparta.legendofdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.legendofdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.legendofdelivery.domain.order.entity.Order;
import com.sparta.legendofdelivery.domain.order.entity.OrderStatusEnum;
import com.sparta.legendofdelivery.domain.order.service.OrderService;
import com.sparta.legendofdelivery.domain.review.util.TestUtil;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.service.StoreService;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.entity.UserOauth;
import com.sparta.legendofdelivery.domain.user.entity.UserRole;
import com.sparta.legendofdelivery.domain.user.entity.UserStatus;
import com.sparta.legendofdelivery.domain.user.security.UserDetailsImpl;
import com.sparta.legendofdelivery.global.config.SecurityConfig;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = OrderController.class, excludeFilters =
        {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                )
        })
class OrderControllerTest {

    private MockMvc mvc;

    private Principal principal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private StoreService storeService;

    User user = new User();
    Store store = new Store();

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
        getPrincipal();
    }

    private void getPrincipal() throws Exception {

        TestUtil.setField(user, "id", 1L);
        TestUtil.setField(user, "userId", "test1234");
        TestUtil.setField(user, "password", "test1234!");
        TestUtil.setField(user, "name", "이름");
        TestUtil.setField(user, "role", UserRole.USER);
        TestUtil.setField(user, "status", UserStatus.NORMAL);
        TestUtil.setField(user, "oauth", UserOauth.OUR);

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        principal = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

    }

    @Test
    @DisplayName("주문 생성")
    void createOrder() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto();
        // Set necessary fields for requestDto
        TestUtil.setField(requestDto, "storeId", 1L);
        TestUtil.setField(requestDto, "count", 3); // Add appropriate items

        Order order = new Order();
        TestUtil.setField(order, "id", 1L);
        TestUtil.setField(order, "user", user);
        TestUtil.setField(order, "store", store);
        TestUtil.setField(order, "orderStatus", OrderStatusEnum.ACCEPTANCE);
        TestUtil.setField(order, "count", 1);
        TestUtil.setField(order, "totalPrice", 10000L);

        OrderResponseDto responseDto = new OrderResponseDto(order);

        DataResponse<OrderResponseDto> response =
                new DataResponse<>(200, "주문 생성에 성공했습니다.", responseDto);

        when(orderService.createOrder(any(OrderRequestDto.class))).thenReturn(response);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .principal(principal))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }
}