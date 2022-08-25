package shop.dalda.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.dalda.order.OrderStatus;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long id;
    private Long companyId;
    private Long consumerId;
    private Long templateId;
    private String image;
    private String templateResponseList;
    private LocalDateTime orderDate;
    private LocalDateTime pickupDate;
    private String pickupNoticePhone;
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

    public String getTemplateResponseList() {
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
