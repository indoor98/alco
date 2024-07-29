package com.alco.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlcoLogInRequest {

    private final String email;
    private final String password;
}
