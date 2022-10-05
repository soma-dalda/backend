package shop.dalda.user.domain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.user.domain.BusinessHour;
import shop.dalda.user.domain.User;
import shop.dalda.user.ui.dto.UserAuthResponse;
import shop.dalda.user.ui.dto.UserCompanyRequest;

import javax.persistence.EntityManager;

@ActiveProfiles("dev")
@SpringBootTest
class UserRepositoryTest {

    private User user;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @BeforeEach
    @Transactional
    void setUser() throws JsonProcessingException {
        user = User.builder()
                .oauthId("oauth")
                .username("testUser")
                .build();
        UserCompanyRequest companySet = new UserCompanyRequest();
        companySet.setCompanyName("업체명");
        companySet.setCompanyDomain("도메인");
        companySet.setCompanyIntroduction("소개소개");
        companySet.setCompanyPhone("02-1111-1111");
        companySet.setCompanyLocation("서울 강남구");
        companySet.setBusinessHours("[{\"day\":\"월\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
                "{\"day\":\"화\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
                "{\"day\":\"수\",\"start\":\"08:00\",\"end\":\"24:00\"}]");
        companySet.setProfileImage("url");
        companySet.setQnaLink("qnaLink");
        companySet.setInstaLink("instaLink");
        companySet.setEtcLinks("[{\"url\":\"url1\"},{\"url\":\"url3\"},{\"url\":\"url3\"}]");
        user.setCompany(companySet);
        em.persist(user);
    }

    @Transactional
    @Test
    @DisplayName("유저정보 조회")
    void get_user_auth(){
        //given

        UserAuthResponse expectResponse = new UserAuthResponse(
                user.getId(),
                user.getUsername(),
                user.getUserPhone(),
                user.getRole(),
                user.getCompanyName(),
                user.getCompanyIntroduction(),
                user.getCompanyLocation(),
                user.getCompanyDomain(),
                user.getBusinessHours(),
                user.getQnaLink(),
                user.getInstaLink(),
                user.getEtcLinks());
        //when
        UserAuthResponse response = userRepository.getUserAuthById(user.getId());
        //then
        System.out.println("role : " + response.getRole());
        for (BusinessHour hour : response.getBusinessHours()) {
            System.out.printf("%s요일, %s:%s\n",hour.getDay(), hour.getStart(), hour.getEnd());
        }
        Assertions.assertThat(expectResponse).isEqualTo(response);
    }
}