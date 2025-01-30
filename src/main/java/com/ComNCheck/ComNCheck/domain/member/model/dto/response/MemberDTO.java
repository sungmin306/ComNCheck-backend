package com.ComNCheck.ComNCheck.domain.member.model.dto.response;

import com.ComNCheck.ComNCheck.domain.member.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private Long id;
    private String email;
    private String name;
    private String major;
    private int studentNumber;
    private Role role;
}
