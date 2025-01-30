package com.ComNCheck.ComNCheck.domain.member.model.dto.response;

import com.ComNCheck.ComNCheck.domain.member.model.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PresidentDTO {

    private String name;

    public static PresidentDTO of(Member member) {
        return PresidentDTO.builder()
                .name(member.getName())
                .build();
    }
}
