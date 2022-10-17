package shop.dalda.template.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter
public class JPAJsonConverter implements AttributeConverter<List<?>, String> {

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
    public List<?> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
