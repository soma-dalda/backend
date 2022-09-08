package shop.dalda.template;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.dalda.template.dto.request.TemplateRequestDto;
import shop.dalda.template.dto.response.TemplateListResponseDto;
import shop.dalda.template.dto.response.TemplateResponseDto;
import shop.dalda.template.dto.request.TemplateUpdateRequestDto;
import shop.dalda.template.dto.response.TemplateUpdateResponseDto;

import java.net.URI;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
@Slf4j
public class TemplateController {

    private static final String REDIRECT_URL = "/api/templates/%d";

    private final TemplateService templateService;

    // 템플릿 등록
    @Operation(summary = "템플릿 등록", description = "업체에서 주문을 받을 템플릿을 등록하는 메서드")
    @PostMapping("")
    public ResponseEntity<Void> insertTemplate(@Parameter @RequestBody TemplateRequestDto templateRequestDto) throws ParseException {
        Long templateId = templateService.insertTemplate(templateRequestDto);
        String redirectUrl = String.format(REDIRECT_URL, templateId);
        return ResponseEntity.created(URI.create(redirectUrl)).build();
    }

    // 템플릿 조회
    @Operation(summary = "템플릿 조회", description = "템플릿을 선택한 경우 해당 템플릿의 내용을 조회하는 메서드")
    @GetMapping("/{template_id}")
    public ResponseEntity<TemplateResponseDto> selectTemplate(@Parameter(description = "조회할 템플릿의 id") @PathVariable(name = "template_id") Long templateId) throws ParseException {
        TemplateResponseDto templateResponseDto = templateService.selectTemplate(templateId);
        return ResponseEntity.ok(templateResponseDto);
    }

    // 템플릿 목록 조회
    @Operation(summary = "템플릿 목록 조회", description = "업체가 등록한 모든 템플릿의 목록을 조회하는 메서드")
    @GetMapping("/{user_id}/list")
    public ResponseEntity<TemplateListResponseDto> selectTemplateList(@Parameter(description = "조회할 업체의 id") @PathVariable(name = "user_id") Long userId) {
        TemplateListResponseDto templateListResponseDto = templateService.selectTemplateList(userId);
        return ResponseEntity.ok(templateListResponseDto);
    }

    // 템플릿 수정
    @Operation(summary = "템플릿 수정", description = "업체가 등록되어 있던 템플릿의 일부를 수정하는 메서드")
    @PutMapping("/{template_id}")
    public ResponseEntity<TemplateUpdateResponseDto> updateTemplate(/*Principal principal,*/
            @Parameter(description = "수정할 템플릿의 id") @PathVariable(name = "template_id") Long templateId, @Parameter @RequestBody TemplateUpdateRequestDto templateUpdateRequestDto) throws ParseException {
        Long userId = 1L;
        TemplateUpdateResponseDto templateUpdateResponseDto = templateService.updateTemplate(userId, templateId, templateUpdateRequestDto);

        URI redirectUrl = URI.create(String.format(REDIRECT_URL, templateId));

        return ResponseEntity.created(redirectUrl).body(templateUpdateResponseDto);
    }
}
