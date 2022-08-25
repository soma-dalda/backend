package shop.dalda.order.dto.request;

public class OrderRequestDto {

    private Long companyId;
    private Long consumerId;
    private Long templateId;
    private String image;
    private String templateResponseList;
    private Integer[] pickupDate;
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

    public String getTemplateResponseList() {
        return templateResponseList;
    }

    public Integer[] getPickupDate() {
        return pickupDate;
    }

    public String getPickupNoticePhone() {
        return pickupNoticePhone;
    }
}
