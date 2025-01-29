package com.ComNCheck.ComNCheck.domain.Member.model.dto.response;


import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PresidentCouncilResponseDTO {

    private PresidentDTO president;
    private List<CouncilDTO> councilList;

}
