package br.edu.utfpr.prismaacademico.common.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private static final String BLACKLIST_PREFIX = "blacklist:";

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * Adiciona um token à blacklist com TTL baseado no tempo de expiração restante.
     *
     * @param token              o JWT token a ser bloqueado
     * @param expirationSeconds  segundos até a expiração do token
     */
    public void blacklistToken(String token, long expirationSeconds) {
        if (expirationSeconds <= 0) {
            log.debug("Token já expirado, não necessário adicionar à blacklist.");
            return;
        }
        String key = BLACKLIST_PREFIX + token;
        stringRedisTemplate.opsForValue().set(key, "1", Duration.ofSeconds(expirationSeconds));
        log.info("Token adicionado à blacklist por {} segundos.", expirationSeconds);
    }

    /**
     * Verifica se um token está na blacklist.
     *
     * @param token o JWT token a ser verificado
     * @return true se o token está bloqueado
     */
    public boolean isTokenBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        Boolean exists = stringRedisTemplate.hasKey(key);
        return Boolean.TRUE.equals(exists);
    }
}

