package shop.dalda.user.domain.repository;

import shop.dalda.user.ui.dto.UserAuthResponse;
import shop.dalda.user.ui.dto.UserCompanyListResponse;
import shop.dalda.user.ui.dto.UserCompanyResponse;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<UserAuthResponse> getUserAuthById(Long id);
    Optional<UserCompanyResponse> getCompanyByDomain(String companyDomain);
    List<UserCompanyListResponse> getCompanyListByRecentLogin();
}
