package com.ComNCheck.ComNCheck.domain.roleChange.model.dto.request;

import com.ComNCheck.ComNCheck.domain.Member.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleChangeRequestCreateDTO {
    private Long memberId;
    private String name;
    private int studentNumber;
    private String major;
    private String requestPosition;
    private Role  requestRole;
}
