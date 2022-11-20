package shop.dalda.user.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserProfileImageRequest {

    @Schema(description = "이미지 url", defaultValue = "imageUrl")
    private String imageUrl;
}
