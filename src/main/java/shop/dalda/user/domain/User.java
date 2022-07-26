package shop.dalda.user.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;
import shop.dalda.order.domain.Order;
import shop.dalda.template.domain.Template;
import shop.dalda.user.ui.dto.UserCompanyRequest;
import shop.dalda.user.ui.dto.UserProfileImageRequest;
import shop.dalda.user.ui.dto.UserUpdateRequest;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users",indexes = {
        @Index(name = "uix_users_company_domain", columnList = "company_domain", unique = true)
})
@Entity
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "company")
    private List<Order> orderedCompanyList = new ArrayList<>();

    @OneToMany(mappedBy = "consumer")
    private List<Order> orderedConsumerList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Template> templateList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    private String oauthId;

    private String username;

    private String userPhone;

    //업체정보

    private String companyName;

    private String companyLocation;

    private String companyPhone;

    @Column(name = "company_domain")
    private String companyDomain;

    private String companyIntroduction;

    @Column(length = 1000)
    private String profileImage;

    @Convert(converter = BusinessHourConverter.class)
    @Column(columnDefinition = "json")
    private List<BusinessHour> businessHours;

    private String qnaLink;

    private String instaLink;

    @Convert(converter = CompanyLinkConverter.class)
    @Column(columnDefinition = "json")
    private List<CompanyLink> etcLinks;

    private LocalDateTime latestAt;

    private boolean withdraw;

    private LocalDate withdrawAt;

    @Builder
    public User(String oauthId, String username) {
        this.oauthId = oauthId;
        this.username = username;
        this.role = Role.ROLE_MEMBER;
    }

    public void setLatestAt() {
        this.latestAt = LocalDateTime.now();
    }

    public void setCompany(UserCompanyRequest requestDto) throws JsonProcessingException {
        this.companyName = requestDto.getCompanyName();
        this.companyDomain = requestDto.getCompanyDomain();
        this.companyLocation = requestDto.getCompanyLocation();
        this.companyIntroduction = requestDto.getCompanyIntroduction();
        this.businessHours = requestDto.getBusinessHours();
        this.companyPhone = requestDto.getCompanyPhone();
        this.profileImage = requestDto.getProfileImage();
        this.qnaLink = requestDto.getQnaLink();
        this.instaLink = requestDto.getInstaLink();
        this.etcLinks = requestDto.getEtcLinks();
        this.role = Role.ROLE_COMPANY;
    }

    public void updateUserInfo(UserUpdateRequest requestDto) {
        this.username = requestDto.getUsername();
        this.userPhone = requestDto.getUserPhone();
    }

    public void updateProfile(UserProfileImageRequest requestDto) {
        this.profileImage = requestDto.getImageUrl();
    }
}
