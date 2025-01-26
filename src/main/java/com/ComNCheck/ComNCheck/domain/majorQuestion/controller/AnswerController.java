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
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long writerId = principal.getMemberDTO().getId();
        requestDTO.setWriterId(writerId);

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
