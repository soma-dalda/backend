package shop.dalda.template.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateResponseDto {

    private Long id;
    private Long user;
    private JSONArray content;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Long getUser() {
        return user;
    }

    public JSONArray getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}