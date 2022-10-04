package shop.dalda.user.ui.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserCompanyListResponse {
    @Schema(description = "업체명", defaultValue = "업체명")
    String companyName;
    @Schema(description = "업체도메인", defaultValue = "companyDomain")
    String companyDomain;
    @Schema(description = "프로필 이미지", defaultValue = "imageUrl")
    String profileImage;

    @QueryProjection
    public UserCompanyListResponse(String companyName, String companyDomain, String profileImage) {
        this.companyName = companyName;
        this.companyDomain = companyDomain;
        this.profileImage = profileImage;
    }
}
