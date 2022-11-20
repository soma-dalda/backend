package shop.dalda.user.ui.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import shop.dalda.user.domain.BusinessHour;
import shop.dalda.user.domain.CompanyLink;

import java.util.List;

@Data
public class UserCompanyResponse {

    private Long id;

    private String companyName;

    private String companyLocation;

    private String companyPhone;

    private String companyIntroduction;

    private List<BusinessHour> businessHours;

    private String profileImage;

    private String qnaLink;

    private String instaLink;

    private List<CompanyLink> etcLinks;

    @QueryProjection
    public UserCompanyResponse(Long id, String companyName, String companyLocation, String companyPhone, String companyIntroduction, List<BusinessHour> businessHours, String profileImage, String qnaLink, String instaLink, List<CompanyLink> etcLinks) {
        this.id = id;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.companyPhone = companyPhone;
        this.companyIntroduction = companyIntroduction;
        this.businessHours = businessHours;
        this.profileImage = profileImage;
        this.qnaLink = qnaLink;
        this.instaLink = instaLink;
        this.etcLinks = etcLinks;
    }
}
