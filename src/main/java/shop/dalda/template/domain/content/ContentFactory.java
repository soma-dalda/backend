package shop.dalda.template.domain.content;

import org.json.simple.JSONObject;
import shop.dalda.exception.template.UnsupportedTypeContentException;

import java.util.Objects;

public class ContentFactory {
    public static Content create(JSONObject jsonObject) {
        String type = (String) jsonObject.get("type");
        if (Objects.equals(type, "singleObjective") || Objects.equals(type, "multiObjective"))
            return new ObjectiveContent(jsonObject);
        else if (Objects.equals(type, "subjective"))
            return new SubjectiveContent(jsonObject);
        else
            throw new UnsupportedTypeContentException();
    }
}
