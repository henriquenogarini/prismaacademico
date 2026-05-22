package br.edu.utfpr.prismaacademico.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private long expirationMinutes = 120;
    private long refreshExpirationDays = 7;
}

