package com.ComNCheck.ComNCheck.domain.majorQuestion.model.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponseDTO {
    private Long id;
    private String title;
    private String content;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AnswerResponseDTO answer;

    public static QuestionResponseDTO of(

    ) {

    }


}
