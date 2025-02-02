package com.ComNCheck.ComNCheck.domain.member.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

     // 실제 배포에서는 nullable 설정 false 해야함
    @Column(name = "major", nullable = true)
    private String major;

    @Column(name = "student_number")
    private int studentNumber;

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "position")
    private String position;

    @Column(name = "is_check_student_card", nullable = false)
    private boolean checkStudentCard;

    @Builder
    public Member(Long memberId, String email, String name, String major, int studentNumber, Role role) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.major = major;
        this.studentNumber = studentNumber;
        this.position = null;
        this.role = role;
        this.checkStudentCard = false;
    }

    /*
    setter code
    어노테이션으로 안하고 필요한 경우만 setter 설정
     */
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }
    public void updatePosition(String requestPosition) {
        this.position = requestPosition;
    }
    public void updateRole(Role newRole) {
        this.role = newRole;
    }
    public void changeIsCheckStudentCard() {
        checkStudentCard = true;
    }
}

