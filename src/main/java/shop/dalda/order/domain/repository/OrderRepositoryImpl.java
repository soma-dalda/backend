package shop.dalda.order.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import shop.dalda.user.domain.User;

import static shop.dalda.order.domain.QOrder.order;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public OrderRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Long countOrderByUserId(User user) {
        return jpaQueryFactory
                .select(order.count())
                .from(order)
                .where(order.company.eq(user)
                        .or(order.consumer.eq(user)))
                .fetchOne();
    }
}