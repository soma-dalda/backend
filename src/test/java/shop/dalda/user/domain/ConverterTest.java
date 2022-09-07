package shop.dalda.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("dev")
public class ConverterTest {

    @Test
    @DisplayName("BusinessHour 컨버터 테스트")
    void businessHour_convert_test() {
        //given
        List<BusinessHour> entity = new ArrayList<>();
        BusinessHour businessHour1 = BusinessHour.builder()
                .day("월")
                .start("08:00")
                .end("22:00")
                .build();
        BusinessHour businessHour2 = BusinessHour.builder()
                .day("화")
                .start("08:00")
                .end("22:00")
                .build();
        BusinessHour businessHour3 = BusinessHour.builder()
                .day("수")
                .start("08:00")
                .end("24:00")
                .build();

        entity.add(businessHour1);
        entity.add(businessHour2);
        entity.add(businessHour3);

        String jsonFromDB = "[{\"day\":\"월\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
                            "{\"day\":\"화\",\"start\":\"08:00\",\"end\":\"22:00\"}," +
                            "{\"day\":\"수\",\"start\":\"08:00\",\"end\":\"24:00\"}]";
        //when

        BusinessHourConverter converter = new BusinessHourConverter();

        String listToJson = converter.convertToDatabaseColumn(entity);
        List<?> jsonToList = converter.convertToEntityAttribute(jsonFromDB);

        //then
        System.out.println("jsonFromDB\n" + jsonFromDB);
        System.out.println("convert to List\n" + jsonToList);
        System.out.println("------------------------------------------------");
        System.out.println("ListFromEntity\n" + entity);
        System.out.println("convert to json\n" + listToJson);
        Assertions.assertThat(listToJson).isEqualTo(jsonFromDB);
        Assertions.assertThat(jsonToList).isEqualTo(entity);
    }

    @Test
    @DisplayName("CompanyLink 컨버터 테스트")
    void test() {
        //given
        List<CompanyLink> entity = new ArrayList<>();
        CompanyLink link1 = new CompanyLink("title1", "url1");
        CompanyLink link2 = new CompanyLink("title2", "url2");
        CompanyLink link3 = new CompanyLink("title3", "url3");

        entity.add(link1);
        entity.add(link2);
        entity.add(link3);

        String jsonFromDB = "[{\"title\":\"title1\",\"url\":\"url1\"}," +
                            "{\"title\":\"title2\",\"url\":\"url2\"}," +
                            "{\"title\":\"title3\",\"url\":\"url3\"}]";
        //when

        CompanyLinkConverter converter = new CompanyLinkConverter();
        String listToJson = converter.convertToDatabaseColumn(entity);
        List<?> jsonToList = converter.convertToEntityAttribute(jsonFromDB);

        //then
        System.out.println("jsonFromDB\n" + jsonFromDB);
        System.out.println("convert to List\n" + jsonToList);
        System.out.println("------------------------------------------------");
        System.out.println("ListFromEntity\n" + entity);
        System.out.println("convert to json\n" + listToJson);
        Assertions.assertThat(listToJson).isEqualTo(jsonFromDB);
        Assertions.assertThat(jsonToList).isEqualTo(entity);
    }
}
