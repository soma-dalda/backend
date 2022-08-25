package shop.dalda.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.dalda.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByOauthId(String oauthId);
}
