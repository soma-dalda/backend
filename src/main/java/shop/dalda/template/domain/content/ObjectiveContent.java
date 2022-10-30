package shop.dalda.template.domain.content;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import shop.dalda.global.SQLInjectionChecker;
import shop.dalda.order.domain.Answer;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@SuperBuilder
@NoArgsConstructor
public class ObjectiveContent extends Content {
    private List<String> options;
    private int numOfSelect = 1;

    @Override
    void checkDetail() {
        // 중복 제거
        options = options.stream().distinct().collect(Collectors.toList());

        // SQL Injection 처리
        for (int i = 0; i < options.size(); i++) {
            options.set(i, SQLInjectionChecker.checkSQLInjection(options.get(i)));
        }
    }

    @Override
    boolean checkDetailAnswer(Answer answer) {
        // 중복 제거
        answer.setAnswer(answer.getAnswer().stream().distinct().collect(Collectors.toList()));

        // template에 있는 값인지
        for (String s : answer.getAnswer()) {
            if (!options.contains(s)) {
                return false;
            }
        }

        // 선택 개수만큼 선택했는지
        return answer.getAnswer().size() <= numOfSelect;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());

        sb.append("\"options\": [");
        options.forEach(v -> sb.append(v).append(", "));
        sb.replace(sb.length() - 2, sb.length(), "], ");

        sb.append("\"numOfSelect\": ").append(numOfSelect).append("}");

        return sb.toString();
    }
}
