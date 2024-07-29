package com.alco.auth.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * JWT에 담을 정보들을 저장하기 위한 클래스입니다.
 */
@Getter
@AllArgsConstructor
public class PayLoad {

    private final Long memberId;
    private final String nickname;
    private final String authority;

}
