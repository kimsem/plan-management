package com.telco.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger UI 설정을 위한 Configuration 클래스입니다.
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("요금제 관리 시스템 API")
                        .description("통신사 요금제 관리를 위한 REST API 문서")
                        .version("1.0"));
    }
}
