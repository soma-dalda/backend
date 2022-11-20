package shop.dalda.user.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.domain.BusinessHour;
import shop.dalda.user.domain.CompanyLink;
import shop.dalda.user.domain.User;
import shop.dalda.user.ui.dto.UserCompanyRequest;
import shop.dalda.user.ui.dto.UserProfileImageRequest;
import shop.dalda.user.ui.dto.UserUpdateRequest;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dev")
@SpringBootTest
class UserServiceTest {

    private User user;
    private CustomOAuth2User currentUser;
    @Autowired
    UserService userService;
    @Autowired
    EntityManager em;

    @BeforeEach
    @Transactional
    void setUser() {
        user = User.builder()
                .oauthId("oauth")
                .username("testUser")
                .build();
        em.persist(user);
        currentUser = CustomOAuth2User.create(user);
    }

    @Transactional
    @Test
    @DisplayName("유저 정보 수정")
    void update_user() {
        //given
        String afterName = "testUser2";
        String afterPhone = "01000000000";
        UserUpdateRequest requestDto = new UserUpdateRequest();
        requestDto.setUsername(afterName);
        requestDto.setUserPhone(afterPhone);
        //when
        userService.updateUser(currentUser, requestDto);
        //then
        assertThat(user.getUsername()).isEqualTo(afterName);
        assertThat(user.getUserPhone()).isEqualTo(afterPhone);
    }

    @Transactional
    @Test
    @DisplayName("유저 프로필 이미지 수정")
    void update_user_profile() {
        //given
        String afterProfileUrl = "afterUrl";
        UserProfileImageRequest requestDto = new UserProfileImageRequest();
        requestDto.setImageUrl(afterProfileUrl);
        //when
        userService.updateUserProfileImage(currentUser, requestDto);
        //then
        assertThat(user.getProfileImage()).isEqualTo(afterProfileUrl);
    }

    @Transactional
    @Test
    @DisplayName("업체 등록/수정")
    void save_or_update_company() throws Exception {
        //given
        UserCompanyRequest requestDto = new UserCompanyRequest();
        requestDto.setCompanyName("업체명");
        requestDto.setCompanyDomain("도메인");
        requestDto.setCompanyIntroduction("소개소개");
        requestDto.setCompanyPhone("02-1111-1111");
        requestDto.setCompanyLocation("서울 강남구");
        List<BusinessHour> businessHourList = new ArrayList<>();
        businessHourList.add(BusinessHour.builder().day("월").start("08:00").end("22:00").build());
        businessHourList.add(BusinessHour.builder().day("화").start("08:00").end("22:00").build());
        businessHourList.add(BusinessHour.builder().day("수").start("08:00").end("22:00").build());
        requestDto.setBusinessHours(businessHourList);
        requestDto.setProfileImage("url");
        requestDto.setQnaLink("qnaLink");
        requestDto.setInstaLink("instaLink");
        List<CompanyLink> companyLinks = new ArrayList<>();
        companyLinks.add(new CompanyLink("url1","url"));
        companyLinks.add(new CompanyLink("url2","url"));
        requestDto.setEtcLinks(companyLinks);
        //when
        userService.saveOrUpdateCompany(currentUser, requestDto);
        //then
        assertThat(user.getCompanyName()).isEqualTo("업체명");
        assertThat(user.getCompanyIntroduction()).isEqualTo("소개소개");
    }
}