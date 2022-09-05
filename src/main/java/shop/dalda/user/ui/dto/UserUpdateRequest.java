package shop.dalda.user.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Schema(description = "사용자 이름", defaultValue = "사용자명")
    String username;
    @Schema(description = "사용자 전화번호", defaultValue = "01000000000")
    String userPhone;
}
