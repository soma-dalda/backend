package shop.dalda.order.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import shop.dalda.order.domain.Order;
import shop.dalda.order.domain.repository.OrderRepository;
import shop.dalda.order.ui.dto.response.OrderCountResponseDto;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.template.domain.Template;
import shop.dalda.template.domain.repository.TemplateRepository;
import shop.dalda.user.domain.User;
import shop.dalda.user.domain.repository.UserRepository;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

    private CustomOAuth2User currentUser;
    private User user1;
    private User user2;


    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TemplateRepository templateRepository;

    @BeforeEach
    void setUser() {
        user1 = User.builder().build();
        userRepository.save(user1);

        user2 = User.builder().build();
        userRepository.save(user2);

        currentUser = CustomOAuth2User.create(user1);

        templateRepository.save(Template.builder().build());
    }

    @Test
    void countOrder() {
        Template template = templateRepository.findById(1L).orElseThrow();
        for (int i = 0; i < 5; i++) {
            Order testOrder = Order.builder()
                    .company(user1)
                    .consumer(user2)
                    .template(template)
                    .build();
            orderRepository.save(testOrder);
        }
        for (int i = 0; i < 5; i++) {
            Order testOrder = Order.builder()
                    .company(user2)
                    .consumer(user1)
                    .template(template)
                    .build();
            orderRepository.save(testOrder);
        }
        for (int i = 0; i < 5; i++) {
            Order testOrder = Order.builder()
                    .company(user2)
                    .consumer(user2)
                    .template(template)
                    .build();
            orderRepository.save(testOrder);
        }
        OrderCountResponseDto orderCount = orderService.countOrder(currentUser);
        Assertions.assertThat(orderCount.getOrderCount()).isEqualTo(10);
    }
}