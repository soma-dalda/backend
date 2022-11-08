package shop.dalda.content.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ObjectiveContent extends Content {
    private List<String> options;
    private int numOfSelect;

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
