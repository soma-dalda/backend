package shop.dalda.user.ui.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import shop.dalda.user.domain.BusinessHour;
import shop.dalda.user.domain.CompanyLink;

import java.util.List;

@Data
public class UserCompanyResponse {
    String companyName;
    String companyLocation;
    String companyPhone;
    String companyIntroduction;
    List<BusinessHour> businessHours;
    String profileImage;
    String qnaLink;
    String instaLink;
    List<CompanyLink> etcLinks;

    @QueryProjection
    public UserCompanyResponse(String companyName, String companyLocation, String companyPhone, String companyIntroduction, List<BusinessHour> businessHours, String profileImage, String qnaLink, String instaLink, List<CompanyLink> etcLinks) {
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
