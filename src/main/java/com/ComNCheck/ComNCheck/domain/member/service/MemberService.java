package com.ComNCheck.ComNCheck.domain.member.service;

import com.ComNCheck.ComNCheck.domain.member.exception.ValidationException;
import com.ComNCheck.ComNCheck.domain.global.infrastructure.FastApiClient;
import com.ComNCheck.ComNCheck.domain.member.model.dto.response.CouncilDTO;
import com.ComNCheck.ComNCheck.domain.member.model.dto.response.FastApiStudentCardDTO;
import com.ComNCheck.ComNCheck.domain.member.model.dto.response.FastApiStudentCardDTO.ExtractedText;
import com.ComNCheck.ComNCheck.domain.member.model.dto.response.MemberDTO;
import com.ComNCheck.ComNCheck.domain.member.model.dto.response.PresidentCouncilResponseDTO;
import com.ComNCheck.ComNCheck.domain.member.model.dto.response.PresidentDTO;
import com.ComNCheck.ComNCheck.domain.member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.member.model.entity.Role;
import com.ComNCheck.ComNCheck.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final FastApiClient fastApiClient;

    @Transactional
    public MemberDTO registerStudentNumber(Long id, MultipartFile studentCardImage) {
        Member member = memberRepository.findByMemberId(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));
        FastApiStudentCardDTO fastApiResponse = fastApiClient.sendImage(studentCardImage);
        FastApiStudentCardDTO.ExtractedText extractedText = fastApiResponse.getExtractedText();

        validateFastApiResponse(extractedText);
        int studentNumber = Integer.parseInt(extractedText.getStudentId());
        boolean isNameMatch = member.getName().equals(extractedText.getName());
        boolean isMajorMatch = member.getMajor().equals(extractedText.getMajor());

        if (!isNameMatch || !isMajorMatch) {
            throw new ValidationException("이름 또는 전공이 일치하지 않습니다.");
        }
        member.setStudentNumber(studentNumber);
        Member savedMember = memberRepository.save(member);

        return MemberDTO.of(savedMember);
    }

    public PresidentCouncilResponseDTO getPresidentAndCouncils() {
        Member presidentEntity = memberRepository.findByRole(Role.ROLE_MAJOR_PRESIDENT)
                .orElse(null);

        List<Member> councilEntities = memberRepository.findAllByRole(Role.ROLE_STUDENT_COUNCIL);

        PresidentDTO presidentDTO = null;
        if (presidentEntity != null) {
            presidentDTO = PresidentDTO.of(presidentEntity);
        }

        List<CouncilDTO> councilDTOList = councilEntities.stream()
                .map(CouncilDTO::of)
                .collect(Collectors.toList());

        return PresidentCouncilResponseDTO.builder()
                .president(presidentDTO)
                .councilList(councilDTOList)
                .build();
    }


    private void validateFastApiResponse(ExtractedText extractedText) {
        if (extractedText == null ||
                extractedText.getName() == null ||
                extractedText.getMajor() == null ||
                extractedText.getStudentId() == null) {
            throw new ValidationException("FastAPI 응답이 유효하지 않습니다.");
        }
    }

    public MemberDTO getMemberInformation(Long memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));
        return MemberDTO.of(member);

    }
}
