package com.ComNCheck.ComNCheck.domain.member.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FastApiResponseDTO {
    private String message;
    private String similarity;
    @JsonProperty("extracted_text")
    private ExtractedText extractedText;

    @Getter
    @Setter
    public static class ExtractedText {
        private String name;
        @JsonProperty("student_id")
        private String studentId;
        private String major;
    }
}
