package shop.dalda.user.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserUpdateRequest {

    @Schema(description = "사용자 이름", defaultValue = "사용자명")
    private String username;

    @Size(min = 11, max = 11, message = "휴대폰 번호는 11자리로 입력되어야 합니다.")
    @Schema(description = "사용자 전화번호", defaultValue = "01000000000")
    private String userPhone;
}
