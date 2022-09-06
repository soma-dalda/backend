package shop.dalda.s3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class S3ResponseDto {
    private String url;

    public String getUrl() {
        return url;
    }
}
