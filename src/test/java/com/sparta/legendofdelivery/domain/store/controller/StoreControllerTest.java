package com.sparta.legendofdelivery.domain.store.controller;

import com.sparta.legendofdelivery.global.config.SecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(value = StoreController.class, excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = SecurityConfig.class
        )
})
public class StoreControllerTest {

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
//    private StoreService storeService;
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
//        user.setUserId("fdsaffds");
//        user.setRole(UserRole.ADMIN);
//        UserDetailsImpl userDetails = new UserDetailsImpl(user);
//        principal = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//    }
//
//    @Test
//    @DisplayName("가게 생성 성공 테스트")
//    void createStore() throws Exception {
//
//        //given
//        StoreRequestDto requestDto = new StoreRequestDto();
//        requestDto.setCategory(Category.KOREAN);
//        requestDto.setName("name");
//        requestDto.setIntro("intro");
//        Store store = new Store(requestDto);
//        DataResponse<Store> response = new DataResponse<Store>(200, "가게 생성에 성공 하셨습니다.", store);
//
//        when(storeService.createStore(any(StoreRequestDto.class))).thenReturn(response);
//
//        String jsonRequest = objectMapper.writeValueAsString(requestDto);
//
//        //when /then
//        mvc.perform(post("/api/admin/stores")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest)
//                        .principal(principal))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//    }
//
//    @Test
//    @DisplayName("카테고리가 없을때 테스트")
//    void createStoreNonCategory() throws Exception {
//
//        //given
//        StoreRequestDto requestDto = new StoreRequestDto();
//        requestDto.setName("name");
//        requestDto.setIntro("intro");
//        Store store = new Store(requestDto);
//        DataResponse<Store> response = new DataResponse<>(200, "가게 생성에 성공 하셨습니다.", store);
//
//        when(storeService.createStore(any(StoreRequestDto.class))).thenReturn(response);
//
//        String jsonRequest = objectMapper.writeValueAsString(requestDto);
//
//        //when /then
//        mvc.perform(post("/api/admin/stores")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .principal(principal)
//                        .content(jsonRequest))
//                .andExpect(status().is4xxClientError())
//                .andDo(print());
//
//    }
//
//    @Test
//    @DisplayName("가게 name없을때 테스트")
//    void createStoreNonName() throws Exception {
//
//        //given
//        StoreRequestDto requestDto = new StoreRequestDto();
//        requestDto.setCategory(Category.KOREAN);
//        requestDto.setIntro("intro");
//        Store store = new Store(requestDto);
//        DataResponse<Store> response = new DataResponse<Store>(200, "가게 생성에 성공 하셨습니다.", store);
//
//        when(storeService.createStore(any(StoreRequestDto.class))).thenReturn(response);
//
//        String jsonRequest = objectMapper.writeValueAsString(requestDto);
//
//        //when /then
//        mvc.perform(post("/api/admin/stores")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest)
//                        .principal(principal))
//                .andExpect(status().is4xxClientError())
//                .andDo(print());
//
//    }

}
