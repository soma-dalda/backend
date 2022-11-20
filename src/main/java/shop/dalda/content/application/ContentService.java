package shop.dalda.content.application;

import shop.dalda.content.domain.Content;
import shop.dalda.exception.order.InvalidAnswerException;
import shop.dalda.exception.order.RequiredQuestionException;
import shop.dalda.exception.template.TemplateInvalidException;
import shop.dalda.global.SQLInjectionChecker;
import shop.dalda.order.domain.Answer;

public abstract class ContentService {
    // 유효한 템플릿인지 확인
    public void checkValidation(Content content) {
        // 공통 필드
        if (content.getType() == null || content.getQuestion() == null) {
            throw new TemplateInvalidException();
        }
        SQLInjectionChecker.checkSQLInjection(content.getQuestion());

        // 세부 필드
        checkDetail(content);
    }

    // 유효한 답변인지 확인
    public void checkAnswer(Content content, Answer answer) {
        // 필수 문항 답변 여부
        if (content.isRequired() && answer.getAnswer().isEmpty()) {
            throw new RequiredQuestionException();
        }

        // 답변 상세 내용
        if (!checkDetailAnswer(content, answer)) {
            throw new InvalidAnswerException();
        }
    }

    abstract void checkDetail(Content content);

    abstract boolean checkDetailAnswer(Content content, Answer answer);
}
