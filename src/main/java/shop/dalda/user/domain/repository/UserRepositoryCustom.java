package shop.dalda.user.domain.repository;

import shop.dalda.user.ui.dto.UserCompanyResponse;

public interface UserRepositoryCustom {
    public UserCompanyResponse getCompanyByDomain(String companyDomain);
}
