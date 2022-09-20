package shop.dalda.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class OrderRequestDto {

    @Getter
    @Schema(description = "주문을 요청하는 업체의 id", defaultValue = "1")
    private Long companyId;

    @Getter
    @Schema(description = "답변을 제출하는 템플릿의 id", defaultValue = "1")
    private Long templateId;

    @Getter
    @Schema(description = "주문 폼 작성 시 업로드한 이미지의 url", defaultValue = "https://dalda.img.s3.ap-northeast-2.amazonaws.com/original/b0daf881-ed9a-43ef-bb0f-e4dcffe5b76b-Frame.png")
    private String image;

    @Getter
    @Schema(description = "템플릿에 대한 구매자의 답변의 리스트 형태의 문자열", defaultValue = "[{\"question\": \"test\", \"answer\": \"['1', '2']\"}]")
    private String templateResponses;

    @Getter
    @Schema(description = "구매자가 설정한 픽업 일시를 정수형태로 가지는 정수형 배열", defaultValue = "[2022, 9, 7, 12, 00, 00]")
    private String pickupDate;

    @Getter
    @Schema(description = "픽업 알림을 받을 연락처(전화번호) 11자리", defaultValue = "01012341111")
    private String pickupNoticePhone;
}
