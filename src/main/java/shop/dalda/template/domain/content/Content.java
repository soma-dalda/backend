package shop.dalda.template.domain.content;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import shop.dalda.exception.template.TemplateInvalidException;

@Getter
@Setter
public abstract class Content {
    private String type;
    private String question;
    private boolean required;

    public Content(JSONObject jsonObject) {
        try {
            // 필수 요소 확인
            type = (String) jsonObject.get("type");
            question = (String) jsonObject.get("question");
            required = (boolean) jsonObject.getOrDefault("required", true);

        } catch (NullPointerException e) {
            throw new TemplateInvalidException();
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"question\": " + question + ", " +
                "\"required\": " + required + ", ";
    }
}
