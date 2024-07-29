package com.alco.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class MemberLogInRequest {

    private final String email;
    private final String password;

}
