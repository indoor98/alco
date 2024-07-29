package com.alco.auth.service;

import com.alco.auth.domain.PayLoad;
import com.alco.global.dao.RedisDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final Long accessTokenValidityInMilliseconds;
    private final Long refreshTokenValidityInMilliseconds;
    private final RedisDao redisDao;
    public JwtProvider(
            @Value("${spring.jwt.key}") final String secretKey,
            @Value("${spring.jwt.validity.atk}") final long accessTokenValidityInMilliseconds,
            @Value("${spring.jwt.validity.rtk}") final long refreshTokenValidityInMilliseconds,
            final RedisDao redisDao) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
        this.redisDao = redisDao;
    }

    public String createAccessToken(final PayLoad payLoad) {
        return createToken("AccessToken", payLoad, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(final PayLoad payLoad) {

        String refreshToken = createToken("RefreshToken", payLoad, refreshTokenValidityInMilliseconds);
        /* 시간 체크하기 */
        redisDao.setValues(payLoad.getMemberId().toString(), refreshToken, Duration.ofMillis(refreshTokenValidityInMilliseconds));

        return refreshToken;
    }

    public Claims getPayload(String token) {
        return Jwts.parserBuilder()
                    .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private String createToken(final String type,
                               final PayLoad payLoad,
                               final Long validityInMilliseconds) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setSubject(type)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("Authorities", payLoad.getAuthority())
                .claim("id", payLoad.getMemberId())
                .claim("nickname", payLoad.getNickname())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
