package com.ComNCheck.ComNCheck.domain.Member.controller;

import com.ComNCheck.ComNCheck.domain.Member.infrastructure.FastApiClient;
import com.ComNCheck.ComNCheck.domain.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/v1/{id}/student/number")
    public ResponseEntity<String> registerStudentNumber(
            @PathVariable Long id,
            @RequestParam("studentCardImage") MultipartFile studentCardImage) {
        memberService.registerStudentNumber(id, studentCardImage);
        return ResponseEntity.ok("학번이 정상적으로 등록되었습니다.");
    }

}
