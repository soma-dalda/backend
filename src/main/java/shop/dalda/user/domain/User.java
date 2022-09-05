package shop.dalda.user.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;
import shop.dalda.order.Order;
import shop.dalda.template.Template;
import shop.dalda.user.ui.dto.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
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

    @Column(length = 11)
    private String userPhone;

    //업체정보

    private String companyName;

    private String companyLocation;

    private String companyPhone;

    private String companyDomain;

    private String companyIntroduction;

    private String profile;

    @Convert(converter = BusinessHourConverter.class)
    @Column(columnDefinition = "json")
    private List<BusinessHour> businessHours;

    private String qnaLink;

    private String instaLink;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "json")
    private List<CompanyLink> etcLinks;

    private LocalDate latestAt;

    private boolean withdraw;

    private LocalDate withdrawAt;

    @Builder
    public User(String oauthId, String username) {
        this.oauthId = oauthId;
        this.username = username;
        this.role = Role.GUEST;
        this.latestAt = LocalDate.now();
    }

    public void setLatestAt() {
        this.latestAt = LocalDate.now();
    }

    public void setCompany(UserCompanyRequest requestDto) throws JsonProcessingException {
        this.companyName = requestDto.getCompanyName();
        this.companyDomain = requestDto.getCompanyDomain();
        this.companyLocation = requestDto.getCompanyLocation();
        this.companyIntroduction = requestDto.getCompanyIntroduction();
        this.businessHours = requestDto.parsedBusinessHours();
        this.companyPhone = requestDto.getCompanyPhone();
        this.profile = requestDto.getProfile();
        this.qnaLink = requestDto.getQnaLink();
        this.instaLink = requestDto.getInstaLink();
        this.etcLinks = requestDto.parsedEtcLinks();
    }

    public void updateUserInfo(UserUpdateRequest requestDto) {
        this.username = requestDto.getUsername();
        this.userPhone = requestDto.getUserPhone();
    }
}
