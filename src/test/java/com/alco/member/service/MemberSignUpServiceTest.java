package com.alco.member.service;

import com.alco.member.domain.Member;
import com.alco.member.domain.repository.MemberRepository;
import com.alco.member.dto.request.MemberSignUpRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberSignUpServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberSignUpService memberSignUpService;

    @Test
    void testEncodePassword() {
        // Given & Stubbing
        String password = "password";
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        // When
        String encodedPassword = memberSignUpService.encodePassword(password);

        // Then
        assertThat(encodedPassword).isEqualTo("encodedPassword");
        verify(passwordEncoder, times(1)).encode(password);
    }

    @Test
    void testSignUp() {
        // Given & Stubbing
        MemberSignUpRequest request = new MemberSignUpRequest("test@example.com","nickname", "password");

        Member member = Member.of(request);
        when(memberRepository.save(any())).thenReturn(member);

        // When
        memberSignUpService.signUp(request);

        // Then
        verify(memberRepository, times(1)).save(any());

    }

}