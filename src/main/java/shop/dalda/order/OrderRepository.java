package shop.dalda.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.dalda.user.domain.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCompany(User company);

    List<Order> findAllByConsumer(User consumer);
}
