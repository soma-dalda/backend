package shop.dalda.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.dalda.order.dto.request.OrderRequestDto;
import shop.dalda.order.dto.response.OrderListForCompanyResponseDto;
import shop.dalda.order.dto.response.OrderListForConsumerResponseDto;
import shop.dalda.order.dto.response.OrderResponseDto;

import java.net.URI;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private static final String REDIRECT_URL = "/api/orders/%d";

    private final OrderService orderService;

    // 주문 등록
    @PostMapping("")
    public ResponseEntity<Void> order(@RequestBody OrderRequestDto orderRequestDto) {
        Long orderId = orderService.order(orderRequestDto);
        String redirectUrl = String.format(REDIRECT_URL, orderId);
        return ResponseEntity.created(URI.create(redirectUrl)).build();
    }

    // 주문 조회
    @GetMapping("/{order_id}")
    public ResponseEntity<OrderResponseDto> selectOrder(@PathVariable(name = "order_id") Long orderId) {
        OrderResponseDto orderResponseDto = orderService.selectOrder(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }

    // 주문 목록 조회 - 판매자
    @GetMapping("/list/company/{user_id}")
    public ResponseEntity<OrderListForCompanyResponseDto> selectOrderListForCompany(@PathVariable(name = "user_id") Long companyId) {
        OrderListForCompanyResponseDto orderListForCompanyResponseDto = orderService.selectOrderListForCompany(companyId);
        return ResponseEntity.ok(orderListForCompanyResponseDto);
    }

    // 주문 목록 조회 - 구매자
    @GetMapping("/list/consumer/{user_id}")
    public ResponseEntity<OrderListForConsumerResponseDto> selectOrderListForConsumer(@PathVariable(name = "user_id") Long consumerId) {
        OrderListForConsumerResponseDto OrderListForConsumerResponseDto = orderService.selectOrderListForConsumer(consumerId);
        return ResponseEntity.ok(OrderListForConsumerResponseDto);
    }

}