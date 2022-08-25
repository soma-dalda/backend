package shop.dalda.template.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateListResponseDto {

    private Long userId;
    private List<JSONObject> templateList;

    public Long getUserId() {
        return userId;
    }

    public List<JSONObject> getTemplateList() {
        return templateList;
    }

}