package com.ComNCheck.ComNCheck.domain.roleChange.service;


import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.request.RoleChangeRequestDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.ApprovedRoleListDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.RoleChangeListDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.RoleChangeResponseDTO;
import com.ComNCheck.ComNCheck.domain.member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.roleChange.model.entity.RequestStatus;
import com.ComNCheck.ComNCheck.domain.roleChange.model.entity.RoleChange;
import com.ComNCheck.ComNCheck.domain.member.repository.MemberRepository;
import com.ComNCheck.ComNCheck.domain.roleChange.repository.RoleChangeRequestRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoleChangeRequestService {
    private final RoleChangeRequestRepository roleChangeRequestRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public RoleChangeResponseDTO createRoleChangeRequest(Long memberId, RoleChangeRequestDTO requestDTO) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 회원이 없습니다."));

        RoleChange roleChange = RoleChange.builder()
                .member(member)
                .requestPosition(requestDTO.getRequestPosition())
                .requestRole(requestDTO.getRequestRole())
                .build();

        RoleChange saveRoleChange = roleChangeRequestRepository.save(roleChange);
        return RoleChangeResponseDTO.of(saveRoleChange);
    }

    public List<RoleChangeListDTO> getAllRequests() {
        List<RoleChange> requests = roleChangeRequestRepository.findAll();
        return requests.stream()
                .map(RoleChangeListDTO::of)
                .collect(Collectors.toList());
    }

    public RoleChangeResponseDTO getRequestDetail(Long requestId) {
        RoleChange request = roleChangeRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 학생회 신청이 없습니다."));
        return RoleChangeResponseDTO.of(request);
    }

    @Transactional
    public void approveRequest(Long requestId) {
        RoleChange request = roleChangeRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 학생회 신청이 없습니다."));

        request.approve();

        Member member = request.getMember();
        member.updateRole(request.getRequestRole());
        member.updatePosition(request.getRequestPosition());

    }

    public List<ApprovedRoleListDTO> getApproveRequests() {
        List<RoleChange> requests = roleChangeRequestRepository.findAll().stream()
                .filter(req -> req.getStatus() == RequestStatus.APPROVED)
                .collect(Collectors.toList());

        return requests.stream()
                .map(ApprovedRoleListDTO::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeMemberRole(Long requestId, RoleChangeRequestDTO requestDTO) {
        RoleChange request = roleChangeRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("요청이 없습니다."));

        if(request.getStatus() != RequestStatus.APPROVED) {
            throw new IllegalArgumentException("한번 변경된 요청만 수정 가능합니다.");
        }

        Member member = request.getMember();
        member.updateRole(requestDTO.getRequestRole());
        member.updatePosition(requestDTO.getRequestPosition());
    }

    @Transactional
    public void deleteRequest(Long requestId) {
        RoleChange request = roleChangeRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 학생회 신청이 없습니다."));
        roleChangeRequestRepository.delete(request);
    }
}
