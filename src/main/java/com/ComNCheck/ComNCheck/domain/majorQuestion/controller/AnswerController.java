package com.ComNCheck.ComNCheck.domain.majorQuestion.controller;

import com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.request.AnswerRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.response.AnswerResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorQuestion.service.AnswerService;
import com.ComNCheck.ComNCheck.domain.security.oauth.CustomOAuth2Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/major/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    @PostMapping
    public ResponseEntity<AnswerResponseDTO> createOrUpdateAnswer(
            @RequestBody AnswerRequestDTO requestDTO,
            Authentication authentication
    ) {
        // 현재 로그인된 사용자 ID를 사용해 writerId 설정(학생회 역할 검증 등은 Service에서 처리)
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long writerId = principal.getMemberDTO().getId();
        requestDTO.setWriterId(writerId);

        // 실제로는 Role 확인(학생회인지) 로직이 필요할 수 있음
        AnswerResponseDTO responseDTO = answerService.createOrUpdateAnswer(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/{answerId}")
    public ResponseEntity<AnswerResponseDTO> updateAnswer(
            @PathVariable Long answerId,
            @RequestBody String content,
            Authentication authentication
    ) {
        AnswerResponseDTO responseDTO = answerService.updateAnswer(answerId, content);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long answerId,
            Authentication authentication
    ) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
}
