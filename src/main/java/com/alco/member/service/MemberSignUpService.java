package com.alco.member.service;

import com.alco.member.domain.Member;
import com.alco.member.domain.repository.MemberRepository;
import com.alco.member.dto.request.MemberSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberSignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(MemberSignUpRequest request) {
        String encodedPassword = encodePassword(request.getPassword());

        request.setPassword(encodedPassword);

        memberRepository.save(Member.of(request));
    }

    String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
