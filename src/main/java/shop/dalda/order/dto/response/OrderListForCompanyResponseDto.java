package shop.dalda.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListForCompanyResponseDto {
    @Schema(description = "목록을 조회한 업체의 id", defaultValue = "1")
    private Long userId;
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
    private JSONArray orderList;

    public Long getUserId() {
        return userId;
    }

    public JSONArray getOrderList() {
        return orderList;
    }

}
