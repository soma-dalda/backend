package shop.dalda.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // Info
        Info info = new Info().title("Dalda API 명세서")
                .description("Dalda API 명세서 입니다")
                .version("v1");
        // Server
        List<Server> servers = List.of(new Server()
                .url("https://dev.dalda.shop")
                .description("https request"));

        io.swagger.v3.oas.models.security.SecurityScheme securityScheme =
                    new io.swagger.v3.oas.models.security.SecurityScheme()
                            .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
                            .name("Authorization");
            SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

            return new OpenAPI()
                    .servers(servers)
                    .components(new Components().addSecuritySchemes("bearerAuth",securityScheme))
                    .security(List.of(securityRequirement))
                    .info(info);
    }
}
