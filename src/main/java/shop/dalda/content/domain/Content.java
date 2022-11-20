package shop.dalda.content.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ObjectiveContent.class, name = "singleObjective"),
        @JsonSubTypes.Type(value = ObjectiveContent.class, name = "multiObjective"),
        @JsonSubTypes.Type(value = SubjectiveContent.class, name = "subjective")})
public abstract class Content {
    private String type;
    private String question;
    private boolean required;
    private String imageUrl;


    @Override
    public String toString() {
        return "{" +
                "\"type\": " + type + ", " +
                "\"question\": " + question + ", " +
                "\"required\": " + required + ", " +
                "\"imageUrl\": " + imageUrl + ", ";
    }
}
