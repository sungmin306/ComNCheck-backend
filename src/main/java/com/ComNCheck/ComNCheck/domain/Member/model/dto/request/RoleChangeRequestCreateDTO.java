package com.ComNCheck.ComNCheck.domain.Member.model.dto.request;

import com.ComNCheck.ComNCheck.domain.Member.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleChangeRequestCreateDTO {
    private Long memberId;
    private Role requestRole;
}
