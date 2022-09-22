package shop.dalda.order.domain;

import lombok.*;
import shop.dalda.order.ui.mapper.AnswerConverter;
import shop.dalda.template.domain.Template;
import shop.dalda.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private User company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private User consumer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "img_url")
    private String image;

    @Convert(converter = AnswerConverter.class)
    @Column(columnDefinition = "json")
    private List<Answer> templateResponses;

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
}
