package com.ComNCheck.ComNCheck.domain.majorQuestion.controller;

import com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.request.QuestionRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.response.QuestionResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorQuestion.service.QuestionService;
import com.ComNCheck.ComNCheck.domain.security.oauth.CustomOAuth2Member;
import java.net.URI;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping("/api/v1/major/questions")
@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;


    @PostMapping
    public ResponseEntity<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO requestDTO) {
        QuestionResponseDTO responseDTO = questionService.createQuestion(requestDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> getQuestion(@PathVariable Long id) {
        QuestionResponseDTO responseDTO = questionService.getQuestion(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestion() {
        List<QuestionResponseDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/my")
    public ResponseEntity<List<QuestionResponseDTO>> getMyQuestions(Authentication authentication) {
         CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
         Long writerId = principal.getMemberDTO().getMemberId();
         List<QuestionResponseDTO> myQuestions= questionService.getMyQuestions(writerId);
         return ResponseEntity.ok(myQuestions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(@PathVariable Long id,
                                                              @RequestBody QuestionRequestDTO requestDTO,
                                                              Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        QuestionResponseDTO updateDTO = questionService.updateQuestion(id, requestDTO, memberId);
        return ResponseEntity.ok(updateDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id, Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        questionService.deleteQuestion(id, memberId);
        return ResponseEntity.noContent().build();
    }


}
