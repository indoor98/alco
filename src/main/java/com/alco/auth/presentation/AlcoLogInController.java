package com.alco.auth.presentation;

import com.alco.auth.dto.request.AlcoLogInRequest;
import com.alco.auth.dto.response.JwtTokenResponse;
import com.alco.auth.service.AlcoLogInService;
import com.alco.global.cookie.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AlcoLogInController {

    private final AlcoLogInService alcoLoginService;
    private final CookieUtil cookieUtil;

    @PostMapping("api/auth/login")
    public ResponseEntity<String> logIn(@RequestBody AlcoLogInRequest request,
                                        HttpServletResponse response) {
        try {
            JwtTokenResponse jwtTokenResponse = alcoLoginService.logIn(request);
            String cookie = cookieUtil.createRefreshTokenCookie(jwtTokenResponse.getRefreshToken());
            response.addHeader(HttpHeaders.SET_COOKIE, cookie);
            return ResponseEntity.ok(jwtTokenResponse.getAccessToken());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("api/auth/test")
    public ResponseEntity<Void> testApi(
            @CookieValue(value = "RefreshToken", defaultValue = "") String RefreshToken) {
        return ResponseEntity.ok(null);
    }

}
