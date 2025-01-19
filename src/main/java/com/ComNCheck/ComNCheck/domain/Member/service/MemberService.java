package com.ComNCheck.ComNCheck.domain.Member.service;

import com.ComNCheck.ComNCheck.domain.Member.exception.ValidationException;
import com.ComNCheck.ComNCheck.domain.Member.infrastructure.FastApiClient;
import com.ComNCheck.ComNCheck.domain.Member.model.dto.response.FastApiResponseDTO;
import com.ComNCheck.ComNCheck.domain.Member.model.dto.response.FastApiResponseDTO.ExtractedText;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final FastApiClient fastApiClient;
    @Transactional
    public void registerStudentNumber(Long id, MultipartFile studentCardImage) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));
        FastApiResponseDTO fastApiResponse = fastApiClient.sendImage(studentCardImage);
        FastApiResponseDTO.ExtractedText extractedText = fastApiResponse.getExtractedText();

        validateFastApiResponse(extractedText);
        int studentNumber= Integer.parseInt(extractedText.getStudentId());
        boolean isNameMatch = member.getName().equals(extractedText.getName());
        boolean isMajorMatch = member.getMajor().equals(extractedText.getMajor());

        if (isNameMatch && isMajorMatch) {
            member.setStudentNumber(studentNumber);
            memberRepository.save(member);
        } else {
            throw new ValidationException("이름 또는 전공이 일치하지 않습니다.");
        }
    }

    private void validateFastApiResponse(ExtractedText extractedText) {
        if (extractedText == null ||
                extractedText.getName() == null ||
                extractedText.getMajor() == null ||
                extractedText.getStudentId() == null) {
            throw new ValidationException("FastAPI 응답이 유효하지 않습니다.");
        }
    }

}
