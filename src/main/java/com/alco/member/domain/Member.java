package com.alco.member.domain;

import com.alco.member.dto.request.MemberSignUpRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE member SET state 'DELETED' WHERE id = ?")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = true)
    private String password;

    @Column(updatable = true)
    private SocialLoginType socialLoginType = SocialLoginType.ALCO;

    @Column(nullable = true)
    private MemberState state;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime modifiedAt;

    @Column(updatable = true)
    private Authority authority = Authority.USER;

    @Builder
    Member(
            String email,
            String nickname,
            String password,
            SocialLoginType socialLoginType
            ) {

        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.socialLoginType = socialLoginType;
    }

    public static Member of(MemberSignUpRequest request) {
        return Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .build();
    }
}
