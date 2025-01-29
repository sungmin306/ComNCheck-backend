package com.ComNCheck.ComNCheck.domain.Member.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RoleChangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Enumerated(EnumType.STRING)
    private Role requestedRole;

    private String name;
    private String major;
    private int studentNumber;
    private String position;

    @Builder
    public RoleChangeRequest(Member member, Role requestedRole) {
        this.member = member;
        this.requestedRole = requestedRole;
        this.status = RequestStatus.PENDING;
        this.name = member.getName();
        this.major = member.getMajor();
        this.studentNumber = member.getStudentNumber();
        this.position = member.getPosition();
    }

    public void approve() {
        this.status = RequestStatus.APPROVED;
    }

    public void reject() {
        this.status = RequestStatus.REJECTED;
    }

}
