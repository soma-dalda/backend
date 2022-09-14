package shop.dalda.template.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRequestDto {

    @Getter
    @Schema(description = "템플릿 제목", defaultValue = "기본 주문 폼")
    private String title;

    @Getter
    @Schema(description = "템플릿 내용 (Json 형식)", defaultValue = "[{\"type\":\"shortsubjective\",\"required\":true,\"question\":\"등록하기\",\"img\":\"...\"},{\"type\":\"objective\",\"required\":true,\"question\":\"케이크 사이즈를 골라주세요\",\"img\":\"...\",\"options\":[{\"answer\":\"123\",\"img\":\"...\"},{\"answer\":\"1234\"}]}]")
    private String content;
}
