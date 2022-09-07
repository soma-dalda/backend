package shop.dalda.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.dalda.order.OrderStatus;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    @Schema(description = "조회한 주문의 id", example = "1")
    private Long id;
    @Schema(description = "주문을 진행하는 업체의 id", example = "1")
    private Long companyId;
    @Schema(description = "주문을 요청한 구매자의 id", example = "1")
    private Long consumerId;
    @Schema(description = "주문 진행한 템플릿의 id", example = "1")
    private Long templateId;
    @Schema(description = "주문 폼 작성 시 업로드한 이미지의 url", example = "https://dalda.img.s3.ap-northeast-2.amazonaws.com/original/b0daf881-ed9a-43ef-bb0f-e4dcffe5b76b-Frame.png")
    private String image;
    @Schema(description = "구매자의 답변의 리스트 형태의 문자열", example = "[\"[\"abcd\"]\", \"1234\"]")
    private String[] templateResponseList;
    @Schema(description = "주문 제출 일시", example = "2022-09-08T00:25:00")
    private LocalDateTime orderDate;
    @Schema(description = "구매자가 설정한 픽업 일시", example = "2022-09-25T00:25:00")
    private LocalDateTime pickupDate;
    @Schema(description = "픽업 알림을 받을 연락처(전화번호) 11자리", defaultValue = "01012341111")
    private String pickupNoticePhone;
    @Schema(description = "주문 진행 상황", defaultValue = "BEFORE_ACCEPT")
    private OrderStatus orderStatus;

    public Long getId() {
        return id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public String getImage() {
        return image;
    }

    public String[] getTemplateResponseList() {
        return templateResponseList;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    public String getPickupNoticePhone() {
        return pickupNoticePhone;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
