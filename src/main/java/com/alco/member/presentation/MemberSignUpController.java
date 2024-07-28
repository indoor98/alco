package com.alco.member.presentation;

import com.alco.member.dto.request.MemberSignUpRequest;
import com.alco.member.service.MemberSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberSignUpController {

    private final MemberSignUpService memberSignupService;

    @PostMapping("/api/member/signup")
    public ResponseEntity<Void> signUp(@RequestBody MemberSignUpRequest request) {

        memberSignupService.signUp(request);

        return ResponseEntity.ok().build();
    }



}
