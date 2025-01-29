package com.ComNCheck.ComNCheck.domain.Member.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "major", nullable = false)
    private String major;

    @Column(name = "studnet_number", nullable = false)
    private int studentNumber;

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "position")
    private String position;

    @Builder
    public Member(Long id, String email, String name, String major, int studentNumber, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.major = major;
        this.studentNumber = studentNumber;
        this.position = null;
        this.role = role;
    }

    /*
    setter code
    어노테이션으로 안하고 필요한 경우만 setter 설정
     */
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }
    public void updateRole(Role newRole) {
        this.role = newRole;
    }
}

