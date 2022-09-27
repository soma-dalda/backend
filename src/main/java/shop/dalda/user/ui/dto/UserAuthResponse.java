package shop.dalda.user.ui.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import shop.dalda.user.domain.Role;

@Data
public class UserAuthResponse {
    Long id;
    String username;
    String userPhone;
    String role;
    String CompanyDomain;

    @QueryProjection
    public UserAuthResponse(Long id, String username, String userPhone, Role role, String companyDomain) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.role = role.getRole();
        this.CompanyDomain = companyDomain;
    }
}
