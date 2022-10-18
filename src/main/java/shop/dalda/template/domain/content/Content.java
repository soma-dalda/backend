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
    private String imageUrl;

    public Content(JSONObject jsonObject) {
        try {
            // 필수 요소 확인
            type = (String) jsonObject.get("type");
            question = (String) jsonObject.get("question");
            required = (boolean) jsonObject.getOrDefault("required", true);
            imageUrl = (String) jsonObject.getOrDefault("required", null);

        } catch (NullPointerException e) {
            throw new TemplateInvalidException();
        }
    }

    @Override
    public String toString() {
        return "Content{" +
                "type='" + type + '\'' +
                ", question='" + question + '\'' +
                ", required=" + required +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
