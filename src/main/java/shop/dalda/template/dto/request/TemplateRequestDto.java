package shop.dalda.template.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class TemplateRequestDto {

    @Schema(description = "업체 id", defaultValue = "1")
    private Long companyId;
    @Schema(description = "템플릿 제목", defaultValue = "기본 주문 폼")
    private String title;
    @Schema(description = "템플릿 내용 (Json 형식)", defaultValue = "[{\"type\":\"shortsubjective\",\"required\":true,\"question\":\"등록하기\",\"img\":\"...\"},{\"type\":\"objective\",\"required\":true,\"question\":\"케이크 사이즈를 골라주세요\",\"img\":\"...\",\"options\":[{\"answer\":\"123\",\"img\":\"...\"},{\"answer\":\"1234\"}]}]")
    private String content;

    public Long getCompanyId() {
        return companyId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
