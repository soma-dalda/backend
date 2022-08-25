package shop.dalda.user.ui.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    String companyName;
    String companyDomain;
    String companyLocation;
    String companyIntroduction;
    String businessHours;
    String companyPhone;
    String profile;
    String qnaLink;
    String instaLink;
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
