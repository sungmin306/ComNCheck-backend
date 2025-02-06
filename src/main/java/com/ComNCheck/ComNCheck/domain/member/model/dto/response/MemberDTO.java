package com.ComNCheck.ComNCheck.domain.member.model.dto.response;

import com.ComNCheck.ComNCheck.domain.member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.member.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDTO {
    private Long memberId;
    private String email;
    private String name;
    private String major;
    private int studentNumber;
    private Role role;
    private boolean checkStudentCard;

    public MemberDTO() {
    }

}
