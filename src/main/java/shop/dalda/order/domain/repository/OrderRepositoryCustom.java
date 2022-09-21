package shop.dalda.order.domain.repository;

import shop.dalda.user.domain.User;

public interface OrderRepositoryCustom {
    public Long countOrderByUserId(User user);
}


