package shop.dalda.template.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.dalda.exception.template.TemplateNotBelongToUserException;
import shop.dalda.exception.template.TemplateNotFoundException;
import shop.dalda.security.auth.user.CustomOAuth2User;
import shop.dalda.template.domain.content.Content;
import shop.dalda.template.domain.Template;
import shop.dalda.template.domain.mapper.TemplateInfoMapping;
import shop.dalda.template.domain.mapper.TemplateMapper;
import shop.dalda.template.domain.repository.TemplateRepository;
import shop.dalda.template.ui.dto.request.TemplateRequestDto;
import shop.dalda.template.ui.dto.response.TemplateListResponseDto;
import shop.dalda.template.ui.dto.response.TemplateListResponseDto.TemplateInfo;
import shop.dalda.template.ui.dto.response.TemplateResponseDto;
import shop.dalda.exception.user.auth.UserNotFoundException;
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

        //각 질문을 Content 객체로 변환
        templateRequestDto.getContentList().forEach(Content::checkValidation);

        // Template 객체 생성
        Template template = Template.builder()
                .user(user)
                .title(templateRequestDto.getTitle())
                .contentList(templateRequestDto.getContentList())
                .build();

        // DB 저장
        templateRepository.save(template);

        return template.getId();
    }

    public TemplateResponseDto selectTemplate(Long templateId) {
        // Template 객체 생성
        Template template = templateRepository.findById(templateId)
                .orElseThrow(TemplateNotFoundException::new);

        return TemplateMapper.INSTANCE.template2Dto(template);
    }

    public TemplateListResponseDto selectTemplateList(Long userId) {
        // User 객체 생성
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // Template 목록 조회
        List<TemplateInfoMapping> templateInfoList = templateRepository.findAllByUser(user);

        // 반환용 리스트 생성
        List<TemplateInfo> templateListForResponse = new ArrayList<>();
        for (TemplateInfoMapping templateInfoMapping : templateInfoList) {
            TemplateInfo templateInfo = TemplateInfo.builder()
                    .id(templateInfoMapping.getId())
                    .title(templateInfoMapping.getTitle())
                    .build();
            templateListForResponse.add(templateInfo);
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
        template.updateContent(templateUpdateRequestDto.getContentList());

        return TemplateMapper.INSTANCE.template2UpdateDto(template);
    }
}