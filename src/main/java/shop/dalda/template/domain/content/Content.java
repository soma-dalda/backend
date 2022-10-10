package shop.dalda.template.domain.content;

import lombok.Getter;
import org.json.simple.JSONObject;
import shop.dalda.exception.template.TemplateInvalidException;

@Getter
public abstract class Content {
    private String question;
    private boolean required;

    public void checkValidation(JSONObject object) {
        try {
            // 필수 요소 확인
            question = (String) object.get("question");
            required = (boolean) object.get("required");

        } catch (NullPointerException e) {
            throw new TemplateInvalidException();
        }

        checkDetailValidation(object);
    }

    abstract void checkDetailValidation(JSONObject object);

    @Override
    public String toString() {
        return "{" +
                "\"question\": " + question + ", " +
                "\"required\": " + required + ", ";
    }
}
