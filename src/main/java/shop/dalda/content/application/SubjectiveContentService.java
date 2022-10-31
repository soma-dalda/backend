package shop.dalda.content.application;

import org.springframework.stereotype.Service;
import shop.dalda.content.domain.Content;
import shop.dalda.content.domain.SubjectiveContent;
import shop.dalda.exception.template.TemplateInvalidException;
import shop.dalda.global.SQLInjectionChecker;
import shop.dalda.order.domain.Answer;

@Service
public class SubjectiveContentService extends ContentService {

    @Override
    void checkDetail(Content content) {
        SubjectiveContent subjectiveContent = (SubjectiveContent) content;

        // 글자 수 제한이 너무 큰지
        if (subjectiveContent.getTypingLimit() > 1000) {
            throw new TemplateInvalidException();
        }
    }

    @Override
    boolean checkDetailAnswer(Content content, Answer answer) {
        SubjectiveContent subjectiveContent = (SubjectiveContent) content;

        // 제한 글자 수 내로 작성했는지
        if (answer.getAnswer().size() > subjectiveContent.getTypingLimit()) {
            return false;
        }

        // SQL Injection 처리
        answer.getAnswer().set(0, SQLInjectionChecker.checkSQLInjection(answer.getAnswer().get(0)));

        return true;
    }
}
