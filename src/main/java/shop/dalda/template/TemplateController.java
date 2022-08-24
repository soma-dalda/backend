package shop.dalda.template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.dalda.template.dto.request.TemplateRequestDto;
import shop.dalda.template.dto.response.TemplateResponseDto;
import shop.dalda.template.dto.request.TemplateUpdateRequestDto;
import shop.dalda.template.dto.response.TemplateUpdateResponseDto;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TemplateController {

    private static final String REDIRECT_URL = "/api/templates/%d";

    private final TemplateService templateService;

    // 템플릿 등록
    @PostMapping("/templates")
    public ResponseEntity<Void> insertTemplate(@RequestBody TemplateRequestDto templateRequestDto) throws ParseException {
        Long templateId = templateService.insertTemplate(templateRequestDto);
        String redirectUrl = String.format(REDIRECT_URL, templateId);

        return ResponseEntity.created(URI.create(redirectUrl)).build();
    }

    // 템플릿 조회
    @GetMapping("/templates/{template_id}")
    public ResponseEntity<TemplateResponseDto> selectTemplate(@PathVariable(name = "template_id") Long templateId) throws ParseException {
        TemplateResponseDto templateResponseDto = templateService.selectTemplate(templateId);
        return ResponseEntity.ok(templateResponseDto);
    }

    // 템플릿 수정
    @PutMapping("/templates/{template_id}")
    public ResponseEntity<TemplateUpdateResponseDto> updateTemplate(/*Principal principal,*/
            @PathVariable(name = "template_id") Long templateId,
            @RequestBody TemplateUpdateRequestDto templateUpdateRequestDto) throws ParseException {
        Long userId = 1L;
        TemplateUpdateResponseDto templateUpdateResponseDto = templateService.updateTemplate(userId, templateId, templateUpdateRequestDto);

        URI redirectUrl = URI.create(String.format(REDIRECT_URL, templateId));

        return ResponseEntity.created(redirectUrl).body(templateUpdateResponseDto);
    }
}
