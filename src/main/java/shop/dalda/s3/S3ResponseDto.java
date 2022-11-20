package shop.dalda.s3;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class S3ResponseDto {
    @Schema(description = "업로드된 이미지 url", example = "https://dalda.img.s3.ap-northeast-2.amazonaws.com/original/b0daf881-ed9a-43ef-bb0f-e4dcffe5b76b-Frame.png")
    private String url;

    public String getUrl() {
        return url;
    }
}
