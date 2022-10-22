package shop.dalda.order.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.dalda.exception.user.auth.UserNotFoundException;
import shop.dalda.exception.order.OrderNotBelongToUserException;
import shop.dalda.exception.order.OrderNotFoundException;
import shop.dalda.exception.template.TemplateNotFoundException;
import shop.dalda.order.domain.Order;
import shop.dalda.order.domain.OrderStatus;
import shop.dalda.order.domain.repository.OrderRepository;
import shop.dalda.order.ui.dto.request.OrderUpdateRequestDto;
import shop.dalda.order.ui.dto.response.OrderUpdateResponseDto;
import shop.dalda.order.ui.dto.request.OrderRequestDto;
import shop.dalda.order.ui.dto.response.OrderCountResponseDto;
import shop.dalda.order.ui.dto.response.OrderListForCompanyResponseDto;
import shop.dalda.order.ui.dto.response.OrderListForConsumerResponseDto;
import shop.dalda.order.ui.dto.response.OrderResponseDto;
import shop.dalda.order.ui.mapper.AnswerConverter;
import shop.dalda.order.ui.mapper.OrderMapper;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.template.domain.Template;
import shop.dalda.template.domain.repository.TemplateRepository;
import shop.dalda.user.domain.User;
import shop.dalda.user.domain.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;
    private final OrderRepository orderRepository;

    public Long requestOrder(OrderRequestDto orderRequestDto,
                             CustomOAuth2User authUser) {
        // User, Template 객체 생성
        User company = userRepository.findById(orderRequestDto.getCompanyId())
                .orElseThrow(UserNotFoundException::new);
        User consumer = userRepository.findById(authUser.getId())
                .orElseThrow(UserNotFoundException::new);
        Template template = templateRepository.findById(orderRequestDto.getTemplateId())
                .orElseThrow(TemplateNotFoundException::new);

        // 픽업 시간 객체 생성
        LocalDateTime pickupDateTime = LocalDateTime.parse(orderRequestDto.getPickupDate());

        // Order 객체 생성
        Order order = Order.builder()
                .company(company)
                .consumer(consumer)
                .template(template)
                .image(orderRequestDto.getImage())
                .templateResponses(orderRequestDto.getTemplateResponses())
                .orderDate(LocalDateTime.now())
                .pickupDate(pickupDateTime)
                .pickupNoticePhone(orderRequestDto.getPickupNoticePhone())
                .orderStatus(OrderStatus.BEFORE_ACCEPT)
                .statusChangeDate(LocalDateTime.now())
                .build();

        // DB 저장
        orderRepository.save(order);

        return order.getId();
    }

    public OrderResponseDto selectOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        return OrderResponseDto.builder()
                .id(order.getId())
                .companyId(order.getCompany().getId())
                .consumerId(order.getConsumer().getId())
                .templateId(order.getTemplate().getId())
                .image(order.getImage())
                .templateResponses(order.getTemplateResponses())
                .orderDate(order.getOrderDate())
                .pickupDate(order.getPickupDate())
                .pickupNoticePhone(order.getPickupNoticePhone())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public OrderListForCompanyResponseDto selectOrderListForCompany(CustomOAuth2User authUser) {
        User company = userRepository.findById(authUser.getId())
                .orElseThrow(UserNotFoundException::new);

        List<Order> orderList = orderRepository.findAllByCompany(company);

        OrderListForCompanyResponseDto.OrderListForCompanyResponseDtoBuilder builder = OrderListForCompanyResponseDto.builder();
        for (Order order : orderList) {
            builder = builder.order(OrderListForCompanyResponseDto.OrderForCompany.builder()
                    .id(order.getId())
                    .consumerId(order.getConsumer().getId())
                    .orderStatus(order.getOrderStatus())
                    .build());
        }

        return builder.build();
    }

    public OrderListForConsumerResponseDto selectOrderListForConsumer(CustomOAuth2User authUser) {
        User consumer = userRepository.findById(authUser.getId())
                .orElseThrow(UserNotFoundException::new);

        List<Order> orderList = orderRepository.findAllByConsumer(consumer);

        OrderListForConsumerResponseDto.OrderListForConsumerResponseDtoBuilder builder = OrderListForConsumerResponseDto.builder();
        for (Order order : orderList) {
            builder = builder.order(OrderListForConsumerResponseDto.OrderForConsumer.builder()
                    .id(order.getId())
                    .companyId(order.getConsumer().getId())
                    .companyName(order.getCompany().getCompanyName())
                    .orderStatus(order.getOrderStatus())
                    .status_change_date(order.getStatusChangeDate())
                    .build());
        }

        return builder.build();
    }

    public OrderCountResponseDto countOrder(CustomOAuth2User authUser) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(UserNotFoundException::new);

        int orderCount = orderRepository.countOrdersByCompanyOrConsumer(user, user);

        return OrderCountResponseDto.builder()
                .orderCount(orderCount)
                .build();
    }

    public OrderUpdateResponseDto updateOrder(Long orderId, OrderUpdateRequestDto orderUpdateRequestDto, CustomOAuth2User authUser) {
        // User, Order 조회
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(UserNotFoundException::new);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        // User 권한 확인
        if (!user.equals(order.getCompany()) && !user.equals(order.getConsumer())) {
            throw new OrderNotBelongToUserException();
        }

        // 픽업 시간 객체 생성
        LocalDateTime pickupDateTime = LocalDateTime.parse(orderUpdateRequestDto.getPickupDate());

        // Order update
        order.setImage(orderUpdateRequestDto.getImage());
        order.setTemplateResponses(orderUpdateRequestDto.getTemplateResponses());
        order.setPickupDate(pickupDateTime);
        order.setPickupNoticePhone(orderUpdateRequestDto.getPickupNoticePhone());
        order.setOrderStatus(orderUpdateRequestDto.getOrderStatus());

        return OrderMapper.INSTANCE.orderToDto(order);
    }
}
