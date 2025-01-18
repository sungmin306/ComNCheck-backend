package com.ComNCheck.ComNCheck.domain.Member.service;


import com.ComNCheck.ComNCheck.domain.Member.model.dto.response.StudentNumberDTO;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public void registerStudentNumber(Long id, StudentNumberDTO studentNumberDTO) {
        int studentNumber = studentNumberDTO.getStudentNumber();
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));
        member.setStudentNumber(studentNumber);
        memberRepository.save(member);
    }

}
