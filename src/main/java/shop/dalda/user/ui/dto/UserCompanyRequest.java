package shop.dalda.user.ui.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.dalda.user.domain.BusinessHour;
import shop.dalda.user.domain.CompanyLink;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserCompanyRequest {
    @Schema(description = "업체명", defaultValue = "업체명")
    String companyName;
    @Schema(description = "도메인명", defaultValue = "도메인명")
    String companyDomain;
    @Schema(description = "업체 주소", defaultValue = "업체 주소")
    String companyLocation;
    @Schema(description = "업체 소개", defaultValue = "업체 소개")
    String companyIntroduction;
    @Schema(description = "영업시간", defaultValue = "[{\"day\":\"월\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
            "{\"day\":\"화\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
            "{\"day\":\"수\",\"start\":\"08:00\",\"end\":\"24:00\"}]")
    String businessHours;
    @Schema(description = "업체 연락처", defaultValue = "020000000")
    String companyPhone;
    @Schema(description = "업체 프로필 사진", defaultValue = "imageUrl")
    String profile;
    @Schema(description = "문의 링크", defaultValue = "문의 링크")
    String qnaLink;
    @Schema(description = "인스타 링크", defaultValue = "인스타 링크")
    String instaLink;
    @Schema(description = "기타 링크", defaultValue = "[{\"title\":\"title1\",\"url\":\"url1\"}," +
            "{\"title\":\"title2\",\"url\":\"url2\"}," +
            "{\"title\":\"title3\",\"url\":\"url3\"}]")
    String etcLinks;

    public List<CompanyLink> parsedEtcLinks() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(etcLinks, new TypeReference<>() {});
    }

    public List<BusinessHour> parsedBusinessHours() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(businessHours, new TypeReference<>() {});
    }

}
