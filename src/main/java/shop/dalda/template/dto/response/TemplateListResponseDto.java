package shop.dalda.template.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateListResponseDto {

    @Schema(description = "업체 id", example = "1")
    private Long userId;
    @Schema(description = "템플릿 목록", example = "[\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"title\": \"hello\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"title\": \"기본 폼\"\n" +
            "        }\n" +
            "    ]")
    private List<JSONObject> templateList;

    public Long getUserId() {
        return userId;
    }

    public List<JSONObject> getTemplateList() {
        return templateList;
    }

}