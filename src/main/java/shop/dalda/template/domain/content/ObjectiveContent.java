package shop.dalda.template.domain.content;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import shop.dalda.exception.template.TemplateInvalidException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Getter
@Setter
public class ObjectiveContent extends Content {
    private List<String> options;
    private int numOfSelect;

    public ObjectiveContent(JSONObject jsonObject) {
        super(jsonObject);
        try {
            // 옵션 값들 중복 제거
            options = new ArrayList<>(new LinkedHashSet<>((List<String>) (jsonObject.get("options"))));
            numOfSelect = (int) jsonObject.getOrDefault("numOfSelect", 1);
            options.forEach(v -> {
                // 특수문자 제거 등 처리 필요

            });
        } catch (NullPointerException e) {
            throw new TemplateInvalidException();
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());

        sb.append("\"options\": [");
        options.forEach(v -> sb.append(v).append(", "));
        sb.replace(sb.length() - 2, sb.length(), "], ");

        sb.append("\"numOfSelect\": ").append(numOfSelect).append("}");

        return sb.toString();
    }
}
