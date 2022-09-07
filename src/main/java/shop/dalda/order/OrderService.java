package shop.dalda.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import shop.dalda.exception.UserNotFoundException;
import shop.dalda.exception.template.OrderNotFoundException;
import shop.dalda.exception.template.TemplateNotFoundException;
import shop.dalda.order.dto.request.OrderRequestDto;
import shop.dalda.order.dto.response.OrderListForCompanyResponseDto;
import shop.dalda.order.dto.response.OrderListForConsumerResponseDto;
import shop.dalda.order.dto.response.OrderResponseDto;
import shop.dalda.template.Template;
import shop.dalda.template.TemplateRepository;
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

    public Long order(OrderRequestDto orderRequestDto) {
        // User, Template 객체 생성
        User company = userRepository.findById(orderRequestDto.getCompanyId())
                .orElseThrow(UserNotFoundException::new);
        User consumer = userRepository.findById(orderRequestDto.getCompanyId())
                .orElseThrow(UserNotFoundException::new);
        Template template = templateRepository.findById(orderRequestDto.getTemplateId())
                .orElseThrow(TemplateNotFoundException::new);

        // 픽업 시간 객체 생성
        Integer[] datetime = orderRequestDto.getPickupDate();
        LocalDateTime pickupDateTime = LocalDateTime.of(datetime[0], datetime[1], datetime[2], datetime[3], datetime[4], datetime[5]);

        // 응답 리스트 문자열로 변환
        StringBuilder responseList = new StringBuilder();
        for (String response : orderRequestDto.getTemplateResponseList()) {
            responseList.append(response);
            responseList.append("/");
        }
        responseList.deleteCharAt(responseList.length() - 1);

        // Order 객체 생성
        Order order = Order.builder()
                .company(company)
                .consumer(consumer)
                .template(template)
                .image(orderRequestDto.getImage())
                .templateResponseList(String.valueOf(responseList))
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

        String[] responseList = order.getTemplateResponseList().split("/");

        return OrderResponseDto.builder()
                .id(order.getId())
                .companyId(order.getCompany().getId())
                .consumerId(order.getConsumer().getId())
                .templateId(order.getTemplate().getId())
                .image(order.getImage())
                .templateResponseList(responseList)
                .orderDate(order.getOrderDate())
                .pickupDate(order.getPickupDate())
                .pickupNoticePhone(order.getPickupNoticePhone())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public OrderListForCompanyResponseDto selectOrderListForCompany(Long companyId) {
        User company = userRepository.findById(companyId)
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
                .userId(companyId)
                .orderList(OrderListForResponse)
                .build();
    }

    public OrderListForConsumerResponseDto selectOrderListForConsumer(Long consumerId) {
        User consumer = userRepository.findById(consumerId)
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
                .userId(consumerId)
                .orderList(OrderListForResponse)
                .build();
    }
}
