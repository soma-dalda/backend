package shop.dalda.template.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateResponseDto {

    @Schema(description = "템플릿 id", example = "1")
    private Long id;
    @Schema(description = "업체 id", example = "1")
    private Long userId;
    @Schema(description = "템플릿 제목", example = "기본 주문 폼")
    private String title;
    @Schema(description = "템플릿 내용 (Json 형식)", example = "[\n" +
            "        {\n" +
            "            \"img\": \"...\",\n" +
            "            \"question\": \"postTest\",\n" +
            "            \"type\": \"shortsubjective\",\n" +
            "            \"required\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"img\": \"...\",\n" +
            "            \"question\": \"케이크 사이즈를 골라주세요\",\n" +
            "            \"type\": \"singleObjective\",\n" +
            "            \"options\": [\n" +
            "                \"123\",\n" +
            "                \"1234\"\n" +
            "            ],\n" +
            "            \"required\": true\n" +
            "        }\n" +
            "    ]")
    private JSONArray content;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public JSONArray getContent() {
        return content;
    }
}