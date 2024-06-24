package com.sparta.legendofdelivery.domain.order.service;

import com.sparta.legendofdelivery.domain.order.dto.OrderRequestDto;
import com.sparta.legendofdelivery.domain.order.dto.OrderResponseDto;
import com.sparta.legendofdelivery.domain.order.entity.Order;
import com.sparta.legendofdelivery.domain.order.entity.OrderStatusEnum;
import com.sparta.legendofdelivery.domain.order.repository.OrderRepository;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.repository.StoreRepository;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.domain.user.repository.UserRepository;
import com.sparta.legendofdelivery.domain.user.service.UserService;
import com.sparta.legendofdelivery.global.dto.DataResponse;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import com.sparta.legendofdelivery.global.exception.BadRequestException;
import com.sparta.legendofdelivery.global.exception.NotFoundException;
import com.sparta.legendofdelivery.global.exception.UnauthorizedException;
import com.sparta.legendofdelivery.global.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, StoreRepository storeRepository, JwtProvider jwtProvider, UserService userService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    // 주문 생성
    public DataResponse<OrderResponseDto> createOrder(OrderRequestDto requestDto) {

        User user = userService.getUser();
        Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow(
                () -> new NotFoundException("가게를 찾을 수 없습니다."));

        Order order = new Order(user, store, requestDto);
        orderRepository.save(order);

        return new DataResponse<>(200, "주문 생성에 성공했습니다.", new OrderResponseDto(order));

    }

    // status 상태 변경
    public DataResponse<OrderResponseDto> updateOrderStatus(Long orderId, OrderStatusEnum updateStatus) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("상태 변경하려는 주문을 찾을 수 없습니다."));

        if (order.getOrderStatus() == updateStatus) {
            throw new BadRequestException("이미 같은 상태입니다.");
        }

        order.updateOrderStatus(updateStatus);
        Order saveOrder = orderRepository.save(order);

        OrderResponseDto responseDto = new OrderResponseDto(saveOrder);
        return new DataResponse<>(200, "주문 상태 변경에 성공했습니다.", responseDto);

    }

    // 사용자별 전체 주문 목록 조회
    public DataResponse<List<OrderResponseDto>> getAllOrderByUser(int page, int size, String sortBy) {

        User user = userService.getUser();
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OrderResponseDto> OrderPage = orderRepository.findAllByUser(user, pageable).map(OrderResponseDto::new);
        List<OrderResponseDto> responseDtoList = OrderPage.getContent();

        if (responseDtoList.isEmpty()) {
            throw new NotFoundException("생성된 주문이 없습니다.");
        }

        return new DataResponse<>(200, "주문 전체 목록 조회에 성공했습니다.", responseDtoList);

    }

    // 주문 수정
    @Transactional
    public DataResponse<OrderResponseDto> updateOrder(Long orderId, OrderRequestDto requestDto) {

        User user = userService.getUser();
        // 주문 조회
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));

        // 주문 생성자와 JWT 토큰의 사용자가 일치하는지 확인
        if (!existingOrder.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("본인의 주문만 수정할 수 있습니다.");
        }

        // 가게 조회
        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new NotFoundException("가게를 찾을 수 없습니다."));

        // 업데이트할 주문 객체 생성
        existingOrder.updateOrder(requestDto, user, store);

        Order saveOrder = orderRepository.save(existingOrder);
        OrderResponseDto responseDto = new OrderResponseDto(saveOrder);

        return new DataResponse<>(200, "주문 상태가 수정되었습니다.", responseDto);

    }

    public MessageResponse deleteOrder(Long orderId) {

        User user = userService.getUser();
        // 주문 조회
        Order Order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));
        // 주문 생성자와 JWT 토큰의 사용자가 일치하는지 확인
        if (!Order.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("본인의 주문만 삭제할 수 있습니다.");
        }
        orderRepository.deleteById(orderId);

        return new MessageResponse(200, "주문 삭제에 성공했습니다.");

    }

}