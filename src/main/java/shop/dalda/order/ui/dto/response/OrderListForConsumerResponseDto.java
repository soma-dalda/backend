package shop.dalda.order.ui.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import shop.dalda.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListForConsumerResponseDto {

    @Schema(description = "해당 업체가 진행 중인 주문의 목록(주문 id, 업체 id, 업체 이름, 주문 진행 상태 주문 상태 변경 시각)", example = "[\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"companyId\": 1,\n" +
            "            \"CompanyName\": \"dalda\",\n" +
            "            \"orderDate\": \"2022-09-07T23:44:44\"\n" +
            "            \"orderStatus\": \"BEFORE_ACCEPT\",\n" +
            "            \"statusChangeDate\": \"2022-09-07T23:44:44\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"companyId\": 1,\n" +
            "            \"CompanyName\": \"dalda\",\n" +
            "            \"orderDate\": \"2022-09-07T23:44:44\"\n" +
            "            \"orderStatus\": \"BEFORE_ACCEPT\",\n" +
            "            \"statusChangeDate\": \"2022-09-07T23:44:44\"\n" +
            "        }\n" +
            "    ]")
    @Singular("order")
    private List<OrderForConsumer> orderList;

    @Getter
    @Builder
    public static class OrderForConsumer {
        Long id;
        Long companyId;
        String companyName;
        LocalDateTime orderDate;
        OrderStatus orderStatus;
        LocalDateTime statusChangeDate;
    }
}
