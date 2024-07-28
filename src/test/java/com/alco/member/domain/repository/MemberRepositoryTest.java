package com.alco.member.domain.repository;

import com.alco.global.config.JpaTestConfig;
import com.alco.member.domain.Member;
import com.alco.member.domain.SocialLoginType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(JpaTestConfig.class)
class MemberRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("tester")
                .password("password")
                .socialLoginType(SocialLoginType.ALCO)
                .build();

        entityManager.persist(member);
    }

    @Test
    void testGetMemberByEmail() {

        // Given
        String email = "test@example.com";

        // When
        Member foundMember = memberRepository.findByEmail(email);

        // Then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo(email);

    }

}