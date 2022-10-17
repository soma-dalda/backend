package shop.dalda.template.ui.dto.response;

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
public class TemplateResponseDto {

    @Getter
    @Schema(description = "템플릿 id", example = "1")
    private Long id;

    @Getter
    @Schema(description = "업체 id", example = "1")
    private Long userId;

    @Getter
    @Schema(description = "템플릿 제목", example = "기본 주문 폼")
    private String title;

    @Getter
    @Schema(description = "템플릿 내용", example = "[{\"type\":\"shortsubjective\",\"required\":true,\"question\":\"등록하기\",\"img\":\"...\"},{\"type\":\"objective\",\"required\":true,\"question\":\"케이크 사이즈를 골라주세요\",\"img\":\"...\",\"options\":[\"123\", \"111\"]}]")
    private List<Content> contentList;
}