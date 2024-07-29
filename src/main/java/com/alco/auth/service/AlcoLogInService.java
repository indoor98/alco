package com.alco.auth.service;

import com.alco.auth.domain.PayLoad;
import com.alco.auth.dto.request.AlcoLogInRequest;
import com.alco.auth.dto.response.JwtTokenResponse;
import com.alco.member.domain.Member;
import com.alco.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlcoLogInService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtTokenResponse logIn(AlcoLogInRequest request) {

        if(!validEmailAndPassword(request)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Member member = memberRepository.findByEmail(request.getEmail());

        PayLoad payLoad = new PayLoad(member.getId(), member.getNickname(), member.getAuthority().toString());

        String accessToken = jwtProvider.createAccessToken(payLoad);
        String refreshToken = jwtProvider.createRefreshToken(payLoad);

        return new JwtTokenResponse(accessToken, refreshToken);
    }

    public boolean validEmailAndPassword(AlcoLogInRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail());
        if (member == null) {
            throw new NullPointerException("없는 사용자입니다.");
        }
        return passwordEncoder.matches(request.getPassword(), member.getPassword());
    }

}
