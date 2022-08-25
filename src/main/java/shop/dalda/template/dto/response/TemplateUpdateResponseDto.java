package shop.dalda.template.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateUpdateResponseDto {

    private Long id;
    private Long userId;
    private String title;
    private JSONArray content;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getUserId() {
        return userId;
    }

    public JSONArray getContent() {
        return content;
    }
}
