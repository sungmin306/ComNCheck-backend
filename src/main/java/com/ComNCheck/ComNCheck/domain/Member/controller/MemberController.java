package com.ComNCheck.ComNCheck.domain.Member.controller;

import com.ComNCheck.ComNCheck.domain.Member.model.dto.response.StudentNumberDTO;
import com.ComNCheck.ComNCheck.domain.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/v1/{id}/register-student-number")
    public ResponseEntity<String> registerStudentNumber(
            @PathVariable Long id,
            @RequestBody StudentNumberDTO studentNumberDTO) {
        memberService.registerStudentNumber(id, studentNumberDTO);
        return ResponseEntity.ok("학번이 정상적으로 등록되었습니다.");
    }

}
