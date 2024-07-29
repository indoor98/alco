package com.alco.global.cookie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    private final long refreshTokenValidityInMilliseconds;

    public CookieUtil(
            @Value("${spring.jwt.validity.rtk}") final long refreshTokenValidityInMilliseconds) {
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    public String createRefreshTokenCookie(String refreshToken) {

        /* ResponseCookie를 사용한 이유 정리하기 */
        ResponseCookie cookie = ResponseCookie
                .from("RefreshToken", refreshToken)
                .path("/")
                .httpOnly(true)
                .maxAge(refreshTokenValidityInMilliseconds)
                .build();

        return cookie.toString();
    }

    /* 로그아웃 시 활용 */
    public String createDeleteCookie() {
        ResponseCookie cookie = ResponseCookie
                .from("RefreshToken", "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();

        return cookie.toString();
    }

}
