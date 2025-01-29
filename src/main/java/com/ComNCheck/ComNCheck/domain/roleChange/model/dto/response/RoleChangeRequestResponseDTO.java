package com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response;

import com.ComNCheck.ComNCheck.domain.roleChange.model.entity.RequestStatus;
import com.ComNCheck.ComNCheck.domain.roleChange.model.entity.RoleChangeRequest;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleChangeRequestResponseDTO {

    private Long requestId;
    private Long memberId;
    private String name;
    private String major;
    private int studentNumber;
    private String position;
    private Role requestedRole;
    private RequestStatus status;

    public static RoleChangeRequestResponseDTO of(RoleChangeRequest entity) {
        return RoleChangeRequestResponseDTO.builder()
                .requestId(entity.getRequestId())
                .memberId(entity.getMember().getId())
                .name(entity.getMember().getName())
                .major(entity.getMember().getMajor())
                .studentNumber(entity.getMember().getStudentNumber())
                .position(entity.getRequestPosition())
                .status(entity.getStatus())
                .build();
    }
}