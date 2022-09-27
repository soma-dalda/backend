package shop.dalda.user.domain.repository;

import shop.dalda.user.ui.dto.UserAuthResponse;
import shop.dalda.user.ui.dto.UserCompanyResponse;

public interface UserRepositoryCustom {
    UserAuthResponse getUserAuthById(Long id);
    UserCompanyResponse getCompanyByDomain(String companyDomain);
}
