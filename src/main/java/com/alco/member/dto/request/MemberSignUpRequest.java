package com.alco.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
public class MemberSignUpRequest {

    private final String email;
    private final String nickname;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
}
