package com.ComNCheck.ComNCheck.domain.developerQuestion.model.dto.response;

import com.ComNCheck.ComNCheck.domain.developerQuestion.model.entity.DeveloperQuestion;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeveloperQuestionResponseDTO {
    private Long id;
    private String content;

    public static DeveloperQuestionResponseDTO of(DeveloperQuestion developerQuestion) {
        return DeveloperQuestionResponseDTO.builder()
                .id(developerQuestion.getId())
                .content(developerQuestion.getContent())
                .build();
    }

}
