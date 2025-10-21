package se.moln.integrationgateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Integration Gateway API")
                        .version("v1")
                        .description("Aggregates Product & Order services; Swagger UI at /swagger-ui.html"));
    }
}
