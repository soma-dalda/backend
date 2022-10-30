package shop.dalda.template.domain.content;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shop.dalda.exception.template.TemplateInvalidException;
import shop.dalda.global.SQLInjectionChecker;
import shop.dalda.order.domain.Answer;

@Getter
@SuperBuilder
@NoArgsConstructor
public class SubjectiveContent extends Content {
    private int typingLimit = 100;

    @Override
    void checkDetail() {
        // 글자 수 제한이 너무 큰지
        if (typingLimit > 1000) {
            throw new TemplateInvalidException();
        }
    }

    @Override
    boolean checkDetailAnswer(Answer answer) {
        // 제한 글자 수 내로 작성했는지
        if (answer.getAnswer().size() > typingLimit) {
            return false;
        }

        // SQL Injection 처리
        answer.getAnswer().set(0, SQLInjectionChecker.checkSQLInjection(answer.getAnswer().get(0)));

        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "\"numOfSelect\": " + typingLimit + "}";
    }
}
