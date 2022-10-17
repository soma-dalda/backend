package shop.dalda.template.domain.content;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter
@Setter
public class SubjectiveContent extends Content {
    private int typingLimit;

    public SubjectiveContent(JSONObject jsonObject) {
        super(jsonObject);
        typingLimit = (int) jsonObject.getOrDefault("typingLimit", 100);
    }

    @Override
    public String toString() {
        return super.toString() + "\"numOfSelect\": " + typingLimit + "}";
    }
}
