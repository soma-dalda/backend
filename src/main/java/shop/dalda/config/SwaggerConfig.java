package shop.dalda.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Dalda API 명세서",
                description = "Dalda API 명세서 입니다",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

}
