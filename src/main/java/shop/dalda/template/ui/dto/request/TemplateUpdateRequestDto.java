package shop.dalda.template.ui.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.dalda.template.domain.content.Content;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateUpdateRequestDto {

    @Getter
    @Schema(description = "수정할 템플릿 제목", defaultValue = "특별 주문 폼")
    private String title;

    @Getter
    @Schema(description = "수정할 템플릿 내용 (Json 형식)", defaultValue = "[{\"type\":\"shortsubjective\",\"required\":true,\"question\":\"등록하기\",\"img\":\"...\"},{\"type\":\"objective\",\"required\":true,\"question\":\"케이크 사이즈를 골라주세요\",\"img\":\"...\",\"options\":[\"123\", \"111\"]}]")
    private List<Content> contentList;
}
