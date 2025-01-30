package com.ComNCheck.ComNCheck.domain.member.model.dto.response;

import com.ComNCheck.ComNCheck.domain.member.model.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouncilDTO {
    private String name;
    private String position;

    public static CouncilDTO of(Member member) {
        return CouncilDTO.builder()
                .name(member.getName())
                .position(member.getPosition())
                .build();
    }

}
