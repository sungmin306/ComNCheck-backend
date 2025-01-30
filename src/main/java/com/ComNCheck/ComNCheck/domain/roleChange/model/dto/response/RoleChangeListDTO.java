package com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response;


import com.ComNCheck.ComNCheck.domain.roleChange.model.entity.RoleChange;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleChangeListDTO {

    private Long requestId;
    private String name;
    private String requestPosition;

    public static RoleChangeListDTO of(RoleChange roleChange) {
        return RoleChangeListDTO.builder()
                .requestId(roleChange.getRequestId())
                .name(roleChange.getMember().getName())
                .requestPosition(roleChange.getRequestPosition())
                .build();
    }
}
