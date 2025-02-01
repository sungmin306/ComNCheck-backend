package com.ComNCheck.ComNCheck.domain.member.controller;

import com.ComNCheck.ComNCheck.domain.member.model.dto.response.MemberDTO;
import com.ComNCheck.ComNCheck.domain.member.model.dto.response.MemberInformationResponseDTO;
import com.ComNCheck.ComNCheck.domain.member.model.dto.response.PresidentCouncilResponseDTO;
import com.ComNCheck.ComNCheck.domain.member.service.MemberService;
import com.ComNCheck.ComNCheck.domain.security.oauth.CustomOAuth2Member;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/{memberId}/student/number")
    public ResponseEntity<MemberInformationResponseDTO> registerStudentNumber(
            @PathVariable Long memberId,
            @RequestParam("studentCardImage") MultipartFile studentCardImage) {
        MemberInformationResponseDTO responseDTO = memberService.registerStudentNumber(memberId, studentCardImage);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/members/president-council")
    public ResponseEntity<PresidentCouncilResponseDTO> getPresidentCouncilList() {
        PresidentCouncilResponseDTO responseDTO = memberService.getPresidentAndCouncils();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<MemberInformationResponseDTO> getMemberInformation(Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        MemberInformationResponseDTO responseDTO = memberService.getMemberInformation(memberId);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        memberService.logout(response);
        return ResponseEntity.ok("로그아웃 성공");
    }




}
