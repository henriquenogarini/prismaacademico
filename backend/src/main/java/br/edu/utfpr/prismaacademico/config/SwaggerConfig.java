package br.edu.utfpr.prismaacademico.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(apiServers())
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme()));
    }

    private Info apiInfo() {
        return new Info()
                .title("Prisma Acadêmico API")
                .description("API REST para gestão acadêmica e administrativa do Cursinho Comunitário Prisma da UTFPR-CP.\n\n" +
                        "## Autenticação\n" +
                        "Use o endpoint `POST /api/auth/login` para obter um JWT token.\n" +
                        "Então clique em **Authorize** e informe: `Bearer {seu_token}`\n\n" +
                        "## Usuários Demo\n" +
                        "- `admin@prisma.com` / `123456`\n" +
                        "- `coordination@prisma.com` / `123456`\n" +
                        "- `teacher@prisma.com` / `123456`\n" +
                        "- `student@prisma.com` / `123456`\n" +
                        "- `candidate@prisma.com` / `123456`")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Prisma Acadêmico - UTFPR-CP")
                        .email("prisma@utfpr.edu.br")
                        .url("https://github.com/utfpr-prisma"))
                .license(new License()
                        .name("MIT")
                        .url("https://opensource.org/licenses/MIT"));
    }

    private List<Server> apiServers() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Servidor Local de Desenvolvimento");
        return List.of(localServer);
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Informe o JWT token obtido no endpoint /api/auth/login");
    }
}

