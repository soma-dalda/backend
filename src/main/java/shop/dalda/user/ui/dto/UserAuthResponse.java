package shop.dalda.user.ui.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import shop.dalda.user.domain.BusinessHour;
import shop.dalda.user.domain.CompanyLink;
import shop.dalda.user.domain.Role;

import java.util.List;

@Data
public class UserAuthResponse {

    private Long id;

    private String username;

    private String userPhone;

    private Role role;

    private String companyName;

    private String companyIntroduction;

    private String companyLocation;

    private String companyDomain;

    private List<BusinessHour> businessHours;

    private String qnaLink;

    private String instaLink;

    private List<CompanyLink> etcLinks;

    @QueryProjection
    public UserAuthResponse(Long id, String username, String userPhone, Role role, String companyName, String companyIntroduction, String companyLocation, String companyDomain, List<BusinessHour> businessHours, String qnaLink, String instaLink, List<CompanyLink> etcLinks) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.role = role;
        this.companyName = companyName;
        this.companyIntroduction = companyIntroduction;
        this.companyLocation = companyLocation;
        this.companyDomain = companyDomain;
        this.businessHours = businessHours;
        this.qnaLink = qnaLink;
        this.instaLink = instaLink;
        this.etcLinks = etcLinks;
    }
}
