package com.ComNCheck.ComNCheck.domain.developerQuestion.controller;


import com.ComNCheck.ComNCheck.domain.developerQuestion.model.dto.request.DeveloperQuestionRequestDTO;
import com.ComNCheck.ComNCheck.domain.developerQuestion.model.dto.response.DeveloperQuestionResponseDTO;
import com.ComNCheck.ComNCheck.domain.developerQuestion.service.DeveloperQuestionService;
import com.ComNCheck.ComNCheck.domain.security.oauth.CustomOAuth2Member;
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

@RequestMapping("api/v1/developer/question")
@RequiredArgsConstructor
@RestController
public class DeveloperQuestionController {

    private final DeveloperQuestionService developerQuestionService;

    @PostMapping
    public ResponseEntity<DeveloperQuestionResponseDTO> createDeveloperQuestion(
            @RequestBody DeveloperQuestionRequestDTO requestDTO
            ) {
        DeveloperQuestionResponseDTO createdDTO = developerQuestionService.createDeveloperQuestion(requestDTO);
        URI location = URI.create("api/v1/developer/question/" + createdDTO.getId());
        return ResponseEntity.created(location).body(createdDTO);
    }

    @GetMapping("/{developerQuestionId}")
    public ResponseEntity<DeveloperQuestionResponseDTO> getDeveloperQuestion(
            @PathVariable Long developerQuestionId
    ) {
        DeveloperQuestionResponseDTO responseDTO = developerQuestionService.getDeveloperQuestion(developerQuestionId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<DeveloperQuestionResponseDTO>> getAllDeveloperQuestions() {
        List<DeveloperQuestionResponseDTO> questionList = developerQuestionService.getAllQuestion();
        return ResponseEntity.ok(questionList);
    }

    @PutMapping("/{developerQuestionId}")
    public ResponseEntity<DeveloperQuestionResponseDTO> updateDeveloperQuestion(
            @PathVariable Long developerQuestionId,
            @RequestBody DeveloperQuestionResponseDTO requestDTO,
            Authentication authentication
    ) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long writerId = principal.getMemberDTO().getMemberId();
        DeveloperQuestionResponseDTO updatedDTO =
                developerQuestionService.updateDeveloperQuestion(developerQuestionId, requestDTO, writerId);

        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{developerQuestionId}")
    public ResponseEntity<Void> deleteDeveloperQuestion(
            @PathVariable Long developerQuestionId,
            Authentication authentication
    ) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long writerId = principal.getMemberDTO().getMemberId();
        developerQuestionService.deleteDeveloperQuestion(developerQuestionId, writerId);
        return ResponseEntity.noContent().build();
    }
}
