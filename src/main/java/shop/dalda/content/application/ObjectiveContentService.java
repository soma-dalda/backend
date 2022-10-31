package shop.dalda.content.application;

import org.springframework.stereotype.Service;
import shop.dalda.content.domain.Content;
import shop.dalda.content.domain.ObjectiveContent;
import shop.dalda.global.SQLInjectionChecker;
import shop.dalda.order.domain.Answer;

import java.util.stream.Collectors;

@Service
public class ObjectiveContentService extends ContentService {
    // 유효한 템플릿인지 확인
    @Override
    void checkDetail(Content content) {
        ObjectiveContent objectiveContent = (ObjectiveContent) content;

        // 중복 제거
        objectiveContent.setOptions(objectiveContent.getOptions().stream().distinct().collect(Collectors.toList()));

        // SQL Injection 처리
        for (int i = 0; i < objectiveContent.getOptions().size(); i++) {
            objectiveContent.getOptions().set(i, SQLInjectionChecker.checkSQLInjection(objectiveContent.getOptions().get(i)));
        }
    }

    @Override
    boolean checkDetailAnswer(Content content, Answer answer) {
        ObjectiveContent objectiveContent = (ObjectiveContent) content;

        // 중복 제거
        answer.setAnswer(answer.getAnswer().stream().distinct().collect(Collectors.toList()));

        // template에 있는 값인지
        for (String s : answer.getAnswer()) {
            if (!objectiveContent.getOptions().contains(s)) {
                return false;
            }
        }

        // 선택 개수만큼 선택했는지
        return answer.getAnswer().size() <= objectiveContent.getNumOfSelect();
    }
}
