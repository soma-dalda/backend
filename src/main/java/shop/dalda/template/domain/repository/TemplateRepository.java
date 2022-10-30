package shop.dalda.template.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.dalda.template.domain.Template;
import shop.dalda.template.domain.mapper.TemplateInfoMapping;
import shop.dalda.user.domain.User;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<TemplateInfoMapping> findAllByUser(User user);
}
