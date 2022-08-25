package shop.dalda.user.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shop.dalda.auth.WithMockUser;
import shop.dalda.user.application.UserService;
import shop.dalda.user.domain.CompanyLink;
import shop.dalda.user.ui.dto.UserCompanyRequest;
import shop.dalda.util.service.AuthService;


import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("dev")
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean AuthService authService;
    @MockBean UserService userService;

    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("json 파싱")
    void test() throws JsonProcessingException {
        //given
        UserCompanyRequest request = UserCompanyRequest.builder()
                .companyName("업체명")
                .companyDomain("도메인")
                .companyIntroduction("소개소개")
                .companyPhone("02-1111-1111")
                .companyLocation("서울 강남구")
                .businessHours("[{\"day\":\"월\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
                        "{\"day\":\"화\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
                        "{\"day\":\"수\",\"start\":\"08:00\",\"end\":\"24:00\"}]")
                .profile("url")
                .qnaLink("qnaLink")
                .instaLink("instaLink")
                .etcLinks("[{\"url\":\"url1\"},{\"url\":\"url3\"},{\"url\":\"url3\"}]")
                .build();
        //when
        ObjectMapper mapper = new ObjectMapper();
        List<CompanyLink> result = mapper.readValue(request.getEtcLinks(), new TypeReference<>() {
        });
        //then
        for (CompanyLink c : result) {
            System.out.println(c.getUrl());
        }
    }
    @Test
    @DisplayName("업체등록")
    @WithMockUser()
    void saveOrUpdate_company() throws Exception {
        //given
        UserCompanyRequest request = UserCompanyRequest.builder()
                .companyName("업체명")
                .companyDomain("도메인")
                .companyIntroduction("소개소개")
                .companyPhone("02-1111-1111")
                .companyLocation("서울 강남구")
                .businessHours("[{\"day\":\"월\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
                        "{\"day\":\"화\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
                        "{\"day\":\"수\",\"start\":\"08:00\",\"end\":\"24:00\"}]")
                .profile("url")
                .qnaLink("qnaLink")
                .instaLink("instaLink")
                .etcLinks("[{\"url\":\"url1\"},{\"url\":\"url3\"},{\"url\":\"url3\"}]")
                .build();
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/user-company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(request)));
        //then

        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * EntityManager를 활용하려면 통합테스트 (with @SpringBootTest)를 진행해야 하기 때문에
     * 다른 방안 고민
     *
     * @Test
     *     @DisplayName("업체 조회")
     *     void search_company() throws Exception {
     *         //given
     *         User user = User.builder()
     *                 .oauthId("oauthId")
     *                 .build();
     *         UserCompanyRequest request = UserCompanyRequest.builder()
     *                 .companyName("업체명")
     *                 .companyDomain("도메인")
     *                 .companyIntroduction("소개소개")
     *                 .companyPhone("02-1111-1111")
     *                 .companyLocation("서울 강남구")
     *                 .businessHours("[{\"day\":\"월\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
     *                         "{\"day\":\"화\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
     *                         "{\"day\":\"수\",\"start\":\"08:00\",\"end\":\"24:00\"}]")
     *                 .profile("url")
     *                 .qnaLink("qnaLink")
     *                 .instaLink("instaLink")
     *                 .etcLinks("[{\"url\":\"url1\"},{\"url\":\"url3\"},{\"url\":\"url3\"}]")
     *                 .build();
     *
     *         user.setCompany(request);
     *         em.persist(user);
     *         em.flush();
     *
     *         //when
     *         ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
     *                 .get("/api/user/")
     *                 .param("companyDomain", "도메인"));
     *         //then
     *         resultActions
     *                 .andExpect(status().isOk())
     *                 .andDo(print());
     *     }
     */

    <T> String toJsonString(T data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data);
    }
}