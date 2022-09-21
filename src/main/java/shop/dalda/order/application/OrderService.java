package shop.dalda.order.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import shop.dalda.exception.UserNotFoundException;
import shop.dalda.exception.template.OrderNotFoundException;
import shop.dalda.exception.template.TemplateNotFoundException;
import shop.dalda.order.*;
import shop.dalda.order.domain.Answer;
import shop.dalda.order.domain.Order;
import shop.dalda.order.domain.OrderStatus;
import shop.dalda.order.domain.repository.OrderRepository;
import shop.dalda.order.ui.dto.request.OrderRequestDto;
import shop.dalda.order.ui.dto.response.OrderCountResponseDto;
import shop.dalda.order.ui.dto.response.OrderListForCompanyResponseDto;
import shop.dalda.order.ui.dto.response.OrderListForConsumerResponseDto;
import shop.dalda.order.ui.dto.response.OrderResponseDto;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.template.Template;
import shop.dalda.template.TemplateRepository;
import shop.dalda.user.domain.User;
import shop.dalda.user.domain.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;
    private final OrderRepository orderRepository;

    private final shop.dalda.order.JSONConverter JSONConverter = new JSONConverter();

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

        // 답변 중복 검사
        List<Answer> answers = (List<Answer>) (JSONConverter.convertToEntityAttribute(orderRequestDto.getTemplateResponses()));
        for (Answer answer : answers) {
            answer.setAnswer(answer.getAnswer().substring(2, answer.getAnswer().length() - 2));
            String[] checkedAnswer = answer.getAnswer().split("', '");
            answer.setAnswer(Arrays.toString(Arrays.stream(checkedAnswer).distinct().toArray(String[]::new)));
        }

        // Order 객체 생성
        Order order = Order.builder()
                .company(company)
                .consumer(consumer)
                .template(template)
                .image(orderRequestDto.getImage())
                .templateResponses(answers)
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

        JSONArray OrderListForResponse = new JSONArray();
        for (Order order : orderList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", order.getId());
            jsonObject.put("consumer", order.getConsumer().getId());
            jsonObject.put("order_status", order.getOrderStatus());
            OrderListForResponse.add(jsonObject);
        }

        return OrderListForCompanyResponseDto.builder()
                .orderList(OrderListForResponse)
                .build();
    }

    public OrderListForConsumerResponseDto selectOrderListForConsumer(CustomOAuth2User authUser) {
        User consumer = userRepository.findById(authUser.getId())
                .orElseThrow(UserNotFoundException::new);

        List<Order> orderList = orderRepository.findAllByConsumer(consumer);

        JSONArray OrderListForResponse = new JSONArray();
        for (Order order : orderList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", order.getId());
            jsonObject.put("company", order.getCompany().getId());
            jsonObject.put("order_status", order.getOrderStatus());
            jsonObject.put("status_change_date", order.getStatusChangeDate());
            OrderListForResponse.add(jsonObject);
        }

        return OrderListForConsumerResponseDto.builder()
                .orderList(OrderListForResponse)
                .build();
    }

    public OrderCountResponseDto countOrder(CustomOAuth2User authUser) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(UserNotFoundException::new);

        Long orderCount = orderRepository.countOrderByUserId(user);

        return OrderCountResponseDto.builder()
                .orderCount(orderCount)
                .build();
    }
}
