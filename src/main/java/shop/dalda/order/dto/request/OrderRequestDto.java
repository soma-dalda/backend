package shop.dalda.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrderRequestDto {
    @Schema(description = "주문을 진행할 업체의 id", defaultValue = "1")
    private Long companyId;
    @Schema(description = "주문을 요청하는 구매자의 id", defaultValue = "2")
    private Long consumerId;
    @Schema(description = "답변을 제출하는 템플릿의 id", defaultValue = "1")
    private Long templateId;
    @Schema(description = "주문 폼 작성 시 업로드한 이미지의 url", defaultValue = "https://dalda.img.s3.ap-northeast-2.amazonaws.com/original/b0daf881-ed9a-43ef-bb0f-e4dcffe5b76b-Frame.png")
    private String image;
    @Schema(description = "템플릿에 대한 구매자의 답변의 리스트 형태의 문자열", defaultValue = "[\"[\"abcd\"]\", \"1234\"]")
    private String[] templateResponseList;
    @Schema(description = "구매자가 설정한 픽업 일시를 정수형태로 가지는 정수형 배열", defaultValue = "[2022, 9, 7, 12, 00, 00]")
    private Integer[] pickupDate;
    @Schema(description = "픽업 알림을 받을 연락처(전화번호) 11자리", defaultValue = "01012341111")
    private String pickupNoticePhone;

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

    public Integer[] getPickupDate() {
        return pickupDate;
    }

    public String getPickupNoticePhone() {
        return pickupNoticePhone;
    }
}
