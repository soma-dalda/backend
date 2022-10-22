package shop.dalda.order.ui.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import shop.dalda.order.domain.OrderStatus;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListForCompanyResponseDto {

    @Schema(description = "해당 업체가 진행 중인 주문의 목록(주문 id, 구매자 id, 구매자 닉네임, 진행 상태)", example = "[\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"consumerId\": 1,\n" +
            "            \"consumerName\": \"woongsung\",\n" +
            "            \"order_status\": \"BEFORE_ACCEPT\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"consumer\": 1,\n" +
            "            \"username\": \"woongsung\",\n" +
            "            \"order_status\": \"BEFORE_ACCEPT\"\n" +
            "        }\n" +
            "    ]")
    @Singular("order")
    private List<OrderForCompany> orderList;

    @Getter
    @Builder
    public static class OrderForCompany {
        Long id;
        Long consumerId;
        String consumerName;
        OrderStatus orderStatus;
    }
}
