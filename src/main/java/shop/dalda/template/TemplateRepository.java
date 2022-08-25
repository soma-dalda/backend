package shop.dalda.template;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.dalda.user.domain.User;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findAllByUser(User user);
}
