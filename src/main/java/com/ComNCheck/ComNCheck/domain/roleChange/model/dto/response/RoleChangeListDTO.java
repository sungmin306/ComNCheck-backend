package com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response;


import com.ComNCheck.ComNCheck.domain.Member.model.entity.Role;
import com.ComNCheck.ComNCheck.domain.roleChange.model.entity.RoleChangeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleChangeListDTO {

    private Long requestId;
    private String name;
    private String requestPosition;

    public static RoleChangeListDTO of(RoleChangeRequest roleChangeRequest) {
        return RoleChangeListDTO.builder()
                .requestId(roleChangeRequest.getRequestId())
                .name(roleChangeRequest.getMember().getName())
                .requestPosition(roleChangeRequest.getRequestPosition())
                .build();
    }
}
