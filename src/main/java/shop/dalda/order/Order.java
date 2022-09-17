package shop.dalda.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.dalda.template.Template;
import shop.dalda.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @Getter
    private User company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    @Getter
    private User consumer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    @Getter
    private Template template;

    @Column(name = "img_url")
    @Getter
    private String image;

    @Convert(converter = AnswerConverter.class)
    @Column(columnDefinition = "json")
    @Getter
    private List<Answer> templateResponses;

    @Column(name = "order_date")
    @Getter
    private LocalDateTime orderDate;

    @Column(name = "pickup_date")
    @Getter
    private LocalDateTime pickupDate;

    @Column(name = "pickup_notice_phone")
    @Getter
    private String pickupNoticePhone;

    @Column(name = "order_status")
    @Getter
    private OrderStatus orderStatus;

    @Column(name = "status_change_date")
    @Getter
    private LocalDateTime statusChangeDate;
}
