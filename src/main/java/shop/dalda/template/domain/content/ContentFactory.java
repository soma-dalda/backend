package shop.dalda.template.domain.content;

import shop.dalda.exception.template.UnsupportedTypeContentException;

import java.util.Objects;

public class ContentFactory {
    public static Content create(String type) {
        if (Objects.equals(type, "singleObjective") || Objects.equals(type, "multiObjective"))
            return new ObjectiveContent();
        else if (Objects.equals(type, "shortSubjective") || Objects.equals(type, "multiSubjective"))
            return new SubjectiveContent();
        else
            throw new UnsupportedTypeContentException();
    }
}
