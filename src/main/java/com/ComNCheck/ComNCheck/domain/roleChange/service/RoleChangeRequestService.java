package com.ComNCheck.ComNCheck.domain.roleChange.service;


import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.request.RoleChangeRequestCreateDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.ApprovedRoleListDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.RoleChangeListDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.RoleChangeRequestResponseDTO;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.roleChange.model.entity.RequestStatus;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.Role;
import com.ComNCheck.ComNCheck.domain.roleChange.model.entity.RoleChangeRequest;
import com.ComNCheck.ComNCheck.domain.Member.repository.MemberRepository;
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
    public RoleChangeRequestResponseDTO createRoleChangeRequest(RoleChangeRequestCreateDTO requestDTO) {
        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("등록된 회원이 없습니다."));

        RoleChangeRequest roleChangeRequest = RoleChangeRequest.builder()
                .member(member)
                .requestPosition(requestDTO.getRequestPosition())
                .requestRole(requestDTO.getRequestRole())
                .build();

        RoleChangeRequest saveRoleChangeRequest = roleChangeRequestRepository.save(roleChangeRequest);
        return RoleChangeRequestResponseDTO.of(saveRoleChangeRequest);
    }

    public List<RoleChangeListDTO> getAllRequests() {
        List<RoleChangeRequest> requests = roleChangeRequestRepository.findAll();
        return requests.stream()
                .map(RoleChangeListDTO::of)
                .collect(Collectors.toList());
    }

    public RoleChangeRequestResponseDTO getRequestDetail(Long requestId) {
        RoleChangeRequest request = roleChangeRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 학생회 신청이 없습니다."));
        return RoleChangeRequestResponseDTO.of(request);
    }

    @Transactional
    public void approveRequest(Long requestId) {
        RoleChangeRequest request = roleChangeRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 학생회 신청이 없습니다."));

        request.approve();

        Member member = request.getMember();
        member.updateRole(request.getRequestRole());
        member.updatePosition(request.getRequestPosition());

    }

    public List<ApprovedRoleListDTO> getApproveRequests() {
        List<RoleChangeRequest> requests = roleChangeRequestRepository.findAll().stream()
                .filter(req -> req.getStatus() == RequestStatus.APPROVED)
                .collect(Collectors.toList());

        return requests.stream()
                .map(ApprovedRoleListDTO::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeMemberRole(Long requestId, RoleChangeRequestCreateDTO requestDTO) {
        RoleChangeRequest request = roleChangeRequestRepository.findById(requestId)
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
        RoleChangeRequest request = roleChangeRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 학생회 신청이 없습니다."));
        roleChangeRequestRepository.delete(request);
    }
}
