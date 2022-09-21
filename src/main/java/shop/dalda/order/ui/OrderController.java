package shop.dalda.order.ui;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.dalda.order.application.OrderService;
import shop.dalda.order.ui.dto.request.OrderUpdateRequestDto;
import shop.dalda.order.ui.dto.response.OrderUpdateResponseDto;
import shop.dalda.order.ui.dto.request.OrderRequestDto;
import shop.dalda.order.ui.dto.response.OrderCountResponseDto;
import shop.dalda.order.ui.dto.response.OrderListForCompanyResponseDto;
import shop.dalda.order.ui.dto.response.OrderListForConsumerResponseDto;
import shop.dalda.order.ui.dto.response.OrderResponseDto;
import shop.dalda.security.auth.user.CustomOAuth2User;

import java.net.URI;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private static final String REDIRECT_URL = "/api/orders/%d";

    private final OrderService orderService;

    // 주문 등록
    @Operation(summary = "주문 등록", description = "구매자가 템플릿에 대한 답변을 작성 후 제출 시 주문을 등록하는 메서드")
    @PostMapping("")
    public ResponseEntity<Void> order(@RequestBody OrderRequestDto orderRequestDto,
                                      @Parameter(hidden = true) @AuthenticationPrincipal CustomOAuth2User authUser) {
        Long orderId = orderService.requestOrder(orderRequestDto, authUser);
        String redirectUrl = String.format(REDIRECT_URL, orderId);
        return ResponseEntity.created(URI.create(redirectUrl)).build();
    }

    // 주문 조회
    @Operation(summary = "주문 조회", description = "진행 중 혹은 완료된 주문의 상세 내용을 조회하는 메서드")
    @GetMapping("/{order_id}")
    public ResponseEntity<OrderResponseDto> selectOrder(@Parameter(description = "조회할 주문의 id") @PathVariable(name = "order_id") Long orderId) {
        OrderResponseDto orderResponseDto = orderService.selectOrder(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }

    // 주문 목록 조회 - 판매자
    @Operation(summary = "주문 목록 조회 - 판매자", description = "업체 측에서 현재 자신이 진행 중인 주문의 목록을 조회하는 메서드")
    @GetMapping("/list/company")
    public ResponseEntity<OrderListForCompanyResponseDto> selectOrderListForCompany(@Parameter(hidden = true) @AuthenticationPrincipal CustomOAuth2User authUser) {
        OrderListForCompanyResponseDto orderListForCompanyResponseDto = orderService.selectOrderListForCompany(authUser);
        return ResponseEntity.ok(orderListForCompanyResponseDto);
    }

    // 주문 목록 조회 - 구매자
    @Operation(summary = "주문 목록 조회 - 구매자", description = "구매자가 현재 자신이 진행 중인 주문의 목록을 조회하는 메서드")
    @GetMapping("/list/consumer/{user_id}")
    public ResponseEntity<OrderListForConsumerResponseDto> selectOrderListForConsumer(@Parameter(hidden = true) @AuthenticationPrincipal CustomOAuth2User authUser) {
        OrderListForConsumerResponseDto OrderListForConsumerResponseDto = orderService.selectOrderListForConsumer(authUser);
        return ResponseEntity.ok(OrderListForConsumerResponseDto);
    }

    @Operation(summary = "주문 개수 조회", description = "사용자와 관련된 주문의 개수를 조회하는 메서드")
    @GetMapping("/count")
    public ResponseEntity<OrderCountResponseDto> countOrder(@Parameter(hidden = true) @AuthenticationPrincipal CustomOAuth2User authUser) {
        OrderCountResponseDto orderCountResponseDto = orderService.countOrder(authUser);
        return ResponseEntity.ok(orderCountResponseDto);
    }

    // 템플릿 수정
    @Operation(summary = "주문 수정", description = "주문을 수정하는 메서드")
    @PutMapping("/{order_id}")
    public ResponseEntity<OrderUpdateResponseDto> updateOrder(@Parameter @PathVariable(name = "order_id") Long orderId,
                                                              @Parameter @RequestBody OrderUpdateRequestDto orderUpdateRequestDto,
                                                              @Parameter(hidden = true) @AuthenticationPrincipal CustomOAuth2User authUser) throws ParseException {
        OrderUpdateResponseDto orderUpdateResponseDto = orderService.updateOrder(orderId, orderUpdateRequestDto, authUser);
        URI redirectUrl = URI.create(String.format(REDIRECT_URL, orderId));
        return ResponseEntity.created(redirectUrl).body(orderUpdateResponseDto);
    }
}
