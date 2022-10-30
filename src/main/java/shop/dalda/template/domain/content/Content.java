package shop.dalda.template.domain.content;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import shop.dalda.exception.order.InvalidAnswerException;
import shop.dalda.exception.order.RequiredQuestionException;
import shop.dalda.exception.template.TemplateInvalidException;
import shop.dalda.global.SQLInjectionChecker;
import shop.dalda.order.domain.Answer;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ObjectiveContent.class, name = "singleObjective"),
        @JsonSubTypes.Type(value = ObjectiveContent.class, name = "multiObjective"),
        @JsonSubTypes.Type(value = SubjectiveContent.class, name = "subjective")})
public abstract class Content {
    protected String type;
    protected String question;
    protected boolean required;
    protected String imageUrl;

    // 유효한 템플릿인지 확인
    public void checkValidation() {
        // 공통 필드
        if (type == null || question == null) {
            throw new TemplateInvalidException();
        }
        SQLInjectionChecker.checkSQLInjection(question);

        // 세부 필드
        checkDetail();
    }

    // 유효한 답변인지 확인
    public void checkAnswer(Answer answer) {
        // 필수 문항 답변 여부
        if (required && answer.getAnswer().isEmpty()) {
            throw new RequiredQuestionException();
        }

        // 답변 상세 내용
        if (!checkDetailAnswer(answer)) {
            throw new InvalidAnswerException();
        }
    }

    abstract void checkDetail();

    abstract boolean checkDetailAnswer(Answer answer);

    @Override
    public String toString() {
        return "{" +
                "\"type\": " + type + ", " +
                "\"question\": " + question + ", " +
                "\"required\": " + required + ", " +
                "\"imageUrl\": " + imageUrl + ", ";
    }
}
