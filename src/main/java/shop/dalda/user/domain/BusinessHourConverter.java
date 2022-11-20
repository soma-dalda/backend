package shop.dalda.user.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

/**
 "[
 {\"day\":\"월\",\"start\":\"08:00\,\"end\":\"20:00\"},
 {\"day\":\"화\",\"start\":\"08:00\,\"end\":\"20:00\"},
 .
 .
 ]"
 */

@Converter
public class BusinessHourConverter implements AttributeConverter<List<?>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    //List to JSON
    @Override
    public String convertToDatabaseColumn(List<?> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }

    //JSON to List
    @Override
    public List<BusinessHour> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
