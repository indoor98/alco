package com.alco.global.security;

import com.alco.auth.service.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

/**
 * Json Web Token을 사용한 인증을 수행하는 Provider Class
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtProvider jwtProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (!supports(authentication.getClass())) {
            return null;
        }

        String accessToken = (String) authentication.getPrincipal();

        /* JwtToken 검증, 실패 시 예외 발생 */
        if (accessToken != null) {
            Claims claims = jwtProvider.getClaims(accessToken);
            Long memberId = claims.get("id", Long.class);
            String authority = claims.get("authority", String.class);
            String nickname = claims.get("nickname", String.class);

            return new UsernamePasswordAuthenticationToken(
                    memberId,
                    null,
                    AuthorityUtils
                            .commaSeparatedStringToAuthorityList(authority)
                    );
        } else {
            Authentication failToken = new UsernamePasswordAuthenticationToken(null, null, null);
            failToken.setAuthenticated(false);
            return failToken;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
