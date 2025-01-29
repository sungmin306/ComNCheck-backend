package com.ComNCheck.ComNCheck.domain.Member.model.dto.response;

import com.ComNCheck.ComNCheck.domain.Member.model.entity.RequestStatus;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.RoleChangeRequest;
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
                .requestId(entity.getId())
                .memberId(entity.getMember().getId())
                .name(entity.getName())
                .major(entity.getMajor())
                .studentNumber(entity.getStudentNumber())
                .position(entity.getPosition())
                .requestedRole(entity.getRequestedRole())
                .status(entity.getStatus())
                .build();
    }
}