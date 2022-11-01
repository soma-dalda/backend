package shop.dalda.content.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SubjectiveContent extends Content {
    private int typingLimit = 100;

    @Override
    public String toString() {
        return super.toString() + "\"numOfSelect\": " + typingLimit + "}";
    }
}
