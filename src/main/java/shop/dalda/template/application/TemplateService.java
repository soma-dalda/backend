package shop.dalda.template.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import shop.dalda.exception.template.TemplateNotBelongToUserException;
import shop.dalda.exception.template.TemplateNotFoundException;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.template.domain.content.Content;
import shop.dalda.template.domain.content.ContentFactory;
import shop.dalda.template.domain.Template;
import shop.dalda.template.domain.repository.TemplateRepository;
import shop.dalda.template.ui.dto.request.TemplateRequestDto;
import shop.dalda.template.ui.dto.response.TemplateListResponseDto;
import shop.dalda.template.ui.dto.response.TemplateResponseDto;
import shop.dalda.exception.UserNotFoundException;
import shop.dalda.template.ui.dto.request.TemplateUpdateRequestDto;
import shop.dalda.template.ui.dto.response.TemplateUpdateResponseDto;
import shop.dalda.user.domain.User;
import shop.dalda.user.domain.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;

    public Long insertTemplate(TemplateRequestDto templateRequestDto,
                               CustomOAuth2User authUser) {

        // User 객체 생성
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(UserNotFoundException::new);

        // Template 객체 생성
        Template template = Template.builder()
                .user(user)
                .title(templateRequestDto.getTitle())
                .content(convertToContent(templateRequestDto.getContentList()))
                .build();

        // DB 저장
        templateRepository.save(template);

        return template.getId();
    }

    public TemplateResponseDto selectTemplate(Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(TemplateNotFoundException::new);

        return TemplateResponseDto.builder()
                .id(template.getId())
                .userId(template.getUser().getId())
                .title(template.getTitle())
                .contentList(template.getContent())
                .build();
    }

    public TemplateListResponseDto selectTemplateList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

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

    public TemplateUpdateResponseDto updateTemplate(Long templateId,
                                                    TemplateUpdateRequestDto templateUpdateRequestDto,
                                                    CustomOAuth2User authUser) {

        //User 객체 생성
        User user = userRepository.findById(authUser.getId())
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
        template.updateContent(convertToContent(templateUpdateRequestDto.getContentList()));

        return TemplateUpdateResponseDto.builder().id(template.getId()).userId(template.getUser().getId()).title(template.getTitle())
                .contentList(template.getContent())
                .build();
    }

    //각 질문을 Content 객체로 변환
    public List<Content> convertToContent(List<JSONObject> contents) {
        List<Content> convertedContents = new ArrayList<>();
        for (JSONObject jsonObject : contents) {
            Content content = ContentFactory.create(jsonObject);
            convertedContents.add(content);
        }

        return convertedContents;
    }
}