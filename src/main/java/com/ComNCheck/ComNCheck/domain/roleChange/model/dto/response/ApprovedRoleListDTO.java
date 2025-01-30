package com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response;

import com.ComNCheck.ComNCheck.domain.roleChange.model.entity.RoleChange;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ApprovedRoleListDTO {
    private Long requestId;
    private String name;
    private String requestPosition;

    public static ApprovedRoleListDTO of(RoleChange roleChange) {
        return ApprovedRoleListDTO.builder()
                .requestId(roleChange.getRequestId())
                .name(roleChange.getMember().getName())
                .requestPosition(roleChange.getRequestPosition())
                .build();
    }
}
