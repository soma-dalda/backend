package shop.dalda.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListForConsumerResponseDto {
    private Long userId;
    private List<JSONObject> orderList;

    public Long getUserId() {
        return userId;
    }

    public List<JSONObject> getOrderList() {
        return orderList;
    }

}
