package shop.dalda.template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import shop.dalda.exception.template.TemplateNotBelongToUserException;
import shop.dalda.exception.template.TemplateNotFoundException;
import shop.dalda.template.dto.request.TemplateRequestDto;
import shop.dalda.template.dto.response.TemplateListResponseDto;
import shop.dalda.template.dto.response.TemplateResponseDto;
import shop.dalda.exception.template.TemplateInvalidException;
import shop.dalda.exception.UserNotFoundException;
import shop.dalda.template.dto.request.TemplateUpdateRequestDto;
import shop.dalda.template.dto.response.TemplateUpdateResponseDto;
import shop.dalda.user.domain.User;
import shop.dalda.user.domain.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;

    public Long insertTemplate(TemplateRequestDto templateRequestDto) throws ParseException {
        // User 객체 생성
        User user = userRepository.findById(templateRequestDto.getCompanyId())
                .orElseThrow(UserNotFoundException::new);

        // Template 유효성 검사
        if (!isTemplateValid(templateRequestDto.getContent())) {
            throw new TemplateInvalidException();
        }

        // Template 객체 생성
        Template template = Template.builder()
                .user(user)
                .title(templateRequestDto.getTitle())
                .content(templateRequestDto.getContent())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        // DB 저장
        templateRepository.save(template);

        return template.getId();
    }

    /**
     * @param content
     * @return Boolean
     * @throws ParseException
     */
    private Boolean isTemplateValid(String content) throws ParseException {
        JSONArray jsonArray = convertStringToJsonArray(content);

        //각 질문마다 유효성 검사
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;

            System.out.println(jsonObject.get("type"));
            System.out.println(jsonObject.get("required"));
            System.out.println(jsonObject.get("question"));
            System.out.println(jsonObject.get("img"));
            System.out.println(jsonObject.get("options"));
            System.out.println();
        }

        return true;
    }

    public TemplateResponseDto selectTemplate(Long templateId) throws ParseException {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(TemplateNotFoundException::new);

        return TemplateResponseDto.builder()
                .id(template.getId())
                .userId(template.getUser().getId())
                .title(template.getTitle())
                .content(convertStringToJsonArray(template.getContent()))
                .createdAt(template.getCreatedAt())
                .build();
    }

    public TemplateListResponseDto selectTemplateList(Long userId) throws ParseException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<Template> templateList = templateRepository.findAllByUser(user);

        List<JSONObject> templateListForResponse = new ArrayList<>();
        for (Template template : templateList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", template.getId());
            jsonObject.put("title", template.getTitle());
            templateListForResponse.add(jsonObject);
        }

        return TemplateListResponseDto.builder()
                .userId(user.getId())
                .templateList(templateListForResponse)
                .build();
    }

    public TemplateUpdateResponseDto updateTemplate(Long userId,
                                                    Long templateId,
                                                    TemplateUpdateRequestDto templateUpdateRequestDto) throws ParseException {
        //User 객체 생성
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // Template 조회
        Template template = templateRepository.findById(templateId)
                .orElseThrow(TemplateNotFoundException::new);

        // template 작성자 일치 여부 확인
        if (!template.getUser().equals(user)) {
            throw new TemplateNotBelongToUserException();
        }

        // template 수정
        template.updateTitle(templateUpdateRequestDto.getTitle());
        template.updateContent(templateUpdateRequestDto.getContent());

        return TemplateUpdateResponseDto.builder()
                .id(template.getId())
                .userId(template.getUser().getId())
                .content(convertStringToJsonArray(template.getContent()))
                .build();
    }

    private JSONArray convertStringToJsonArray(String jsonString) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        return (JSONArray) jsonParser.parse(jsonString);
    }
}