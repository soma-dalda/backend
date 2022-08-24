package shop.dalda.template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import shop.dalda.exception.TemplateNotBelongToUserException;
import shop.dalda.exception.TemplateNotFoundException;
import shop.dalda.template.dto.request.TemplateRequestDto;
import shop.dalda.template.dto.response.TemplateResponseDto;
import shop.dalda.exception.TemplateInvalidException;
import shop.dalda.exception.UserNotFoundException;
import shop.dalda.template.dto.request.TemplateUpdateRequestDto;
import shop.dalda.template.dto.response.TemplateUpdateResponseDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;

    public Long insertTemplate(TemplateRequestDto templateRequestDto) throws ParseException {
        // User 객체 생성
        Optional<User> user = userRepository.findById(templateRequestDto.getCompanyId());
        if (user.isEmpty()) {
            throw new UserNotFoundException("유저가 없다");
        }

        // Template 유효성 검사
        if (!isTemplateValid(templateRequestDto.getContent())) {
            throw new TemplateInvalidException();
        }

        // Template 객체 생성
        Template template = Template.builder()
                .user(user.get())
                .content(templateRequestDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        // DB 저장
        templateRepository.save(template);
        System.out.println(template);
        return template.getId();
    }

    /**
     * @param content
     * @return Boolean
     * @throws ParseException
     */
    private Boolean isTemplateValid(String content) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArr = (JSONArray) jsonParser.parse(content);

        //각 질문마다 유효성 검사
        for (Object obj : jsonArr) {
            JSONObject jsonObj = (JSONObject) obj;

            System.out.println(jsonObj.get("type"));
            System.out.println(jsonObj.get("required"));
            System.out.println(jsonObj.get("question"));
            System.out.println(jsonObj.get("img"));
            System.out.println(jsonObj.get("options"));
            System.out.println();
        }

        return true;
    }

    public TemplateResponseDto selectTemplate(Long templateId) throws ParseException {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(TemplateNotFoundException::new);

        return TemplateResponseDto.builder()
                .id(template.getId())
                .user(template.getUser().getId())
                .content(convertStringToJsonArray(template.getContent()))
                .createdAt(template.getCreatedAt())
                .build();
    }

    public TemplateUpdateResponseDto updateTemplate(Long userId,
                                              Long templateId,
                                              TemplateUpdateRequestDto templateUpdateRequestDto) throws ParseException {
        //User 객체 생성
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("유저가 없다");
        }

        // Template 조회
        Template template = templateRepository.findById(templateId)
                .orElseThrow(TemplateNotFoundException::new);

        // template 작성자 일치 여부 확인
        if (!template.getUser().equals(user.get())) {
            throw new TemplateNotBelongToUserException();
        }

        // template 수정
        template.updateContent(templateUpdateRequestDto.getContent());

        return TemplateUpdateResponseDto.builder()
                .id(template.getId())
                .user(template.getUser().getId())
                .content(convertStringToJsonArray(template.getContent()))
                .build();
    }

    private JSONArray convertStringToJsonArray(String jsonString) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        return (JSONArray) jsonParser.parse(jsonString);
    }
}