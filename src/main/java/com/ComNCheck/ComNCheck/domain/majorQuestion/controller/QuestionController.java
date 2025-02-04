package com.ComNCheck.ComNCheck.domain.majorQuestion.controller;

import com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.request.QuestionRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.response.QuestionResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorQuestion.service.QuestionService;
import com.ComNCheck.ComNCheck.domain.security.oauth.CustomOAuth2Member;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import java.util.List;
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
    @Operation(summary = "FAQ 게시글 작성", description = "학생회에게 질문글을 작성할 수 있다.")
    public ResponseEntity<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO requestDTO,
                                                              Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        QuestionResponseDTO responseDTO = questionService.createQuestion(requestDTO, memberId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{majorQuestionId}")
                .buildAndExpand(responseDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{majorQuestionId}")
    @Operation(summary = "fAQ 특정 게시글 조회", description = "FAQ의 특정 게시글을 클릭했을 때 자세히 볼 수 있다")
    public ResponseEntity<QuestionResponseDTO> getQuestion(@PathVariable Long majorQuestionId) {
        QuestionResponseDTO responseDTO = questionService.getQuestion(majorQuestionId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    @Operation(summary = "fAQ의 답변이 달린 게시글 목록 조회", description = "댓글이 달린 모든 게시글 목록을 조회한다.")
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestion() {
        List<QuestionResponseDTO> questions = questionService.getQuestionsWithAnswer();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/my")
    @Operation(summary = "내가 작성한 FAQ 게시글 목록 조회", description = "내가 작성한 FAQ 게시글 목록을 조회한다")
    public ResponseEntity<List<QuestionResponseDTO>> getMyQuestions(Authentication authentication) {
         CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
         Long writerId = principal.getMemberDTO().getMemberId();
         List<QuestionResponseDTO> myQuestions= questionService.getMyQuestions(writerId);
         return ResponseEntity.ok(myQuestions);
    }

    @PutMapping("/{majorQuestionId}")
    @Operation(summary = "FAQ 게시글 수정", description = "FAQ 게시글을 수정한다. 단, 본인이 작성한 게시글만 가능")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(@PathVariable Long majorQuestionId,
                                                              @RequestBody QuestionRequestDTO requestDTO,
                                                              Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        QuestionResponseDTO updateDTO = questionService.updateQuestion(majorQuestionId, requestDTO, memberId);
        return ResponseEntity.ok(updateDTO);
    }

    @DeleteMapping("/{majorQuestionId}")
    @Operation(summary = "FAQ 게시글 삭제 ", description = "FAQ 게시글을 삭제한다. 단, 본인이 작성한 게시글만 가능")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long majorQuestionId, Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        questionService.deleteQuestion(majorQuestionId, memberId);
        return ResponseEntity.noContent().build();
    }


}
