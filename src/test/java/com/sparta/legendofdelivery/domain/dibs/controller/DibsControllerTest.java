package com.sparta.legendofdelivery.domain.dibs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.legendofdelivery.MockSpringSecurityFilter;
import com.sparta.legendofdelivery.domain.dibs.service.DibsService;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.entity.UserRole;
import com.sparta.legendofdelivery.domain.user.security.UserDetailsImpl;
import com.sparta.legendofdelivery.global.config.SecurityConfig;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = DibsController.class, excludeFilters =
        {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                )
        })
class DibsControllerTest {

//    private MockMvc mvc;
//
//    private Principal principal;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private DibsService dibsService;
//
//    @BeforeEach
//    public void setUp() {
//        mvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity(new MockSpringSecurityFilter()))
//                .build();
//        getPrincipal();
//    }
//
//    private void getPrincipal() {
//
//        User user = new User();
//        user.setUserId("testname1234");
//        user.setRole(UserRole.USER);
//        UserDetailsImpl userDetails = new UserDetailsImpl(user);
//        principal = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//    }
//
//    @Test
//    @DisplayName("가게 찜 등록 성공 테스트")
//    void addDibs() throws Exception {
//
//        //given
//        Long storeId = 1L;
//        MessageResponse response = new MessageResponse(200, "가게 찜에 성공했습니다.");
//
//        when(dibsService.addDibs(eq(storeId), any(User.class))).thenReturn(response);
//
//        // when/then
//        mvc.perform(post("/api/dibs/{storeId}", storeId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .principal(principal))
//                .andExpect(status().isOk());
//
//    }

    @Test
    void deleteDibs() {
    }

    @Test
    void getAllDibsByUser() {
    }
}