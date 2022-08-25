package shop.dalda.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.dalda.template.Template;
import shop.dalda.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private User company;

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private User consumer;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "img_url")
    private String image;

    @Column(name = "template_response", columnDefinition = "json")
    private String templateResponseList;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "pickup_date")
    private LocalDateTime pickupDate;

    @Column(name = "pickup_notice_phone")
    private String pickupNoticePhone;

    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "status_change_date")
    private LocalDateTime statusChangeDate;

    public Long getId() {
        return id;
    }

    public User getCompany() {
        return company;
    }

    public User getConsumer() {
        return consumer;
    }

    public Template getTemplate() {
        return template;
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

    public LocalDateTime getStatusChangeDate() {
        return statusChangeDate;
    }
}
