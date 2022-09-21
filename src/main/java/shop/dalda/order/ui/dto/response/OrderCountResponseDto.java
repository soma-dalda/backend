package shop.dalda.order.ui.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderCountResponseDto {

    @Schema(description = "사용자와 관련된 주문 개수", example = "0")
    private Long orderCount;

    @QueryProjection
    public OrderCountResponseDto(Long orderCount) {
        this.orderCount = orderCount;
    }
}
