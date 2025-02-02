package com.ComNCheck.ComNCheck.domain.roleChange.controller;

import com.ComNCheck.ComNCheck.domain.member.model.entity.Role;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.request.RoleChangeRequestDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.ApprovedRoleListDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.RoleChangeListDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.RoleChangeResponseDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.service.RoleChangeRequestService;
import com.ComNCheck.ComNCheck.domain.security.oauth.CustomOAuth2Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/api/v1/role-change-requests")
@RequiredArgsConstructor
@RestController
public class RoleChangeController {

    private final RoleChangeRequestService roleChangeRequestService;

    @PostMapping
    public ResponseEntity<RoleChangeResponseDTO> createRoleChangeRequest(
            @RequestBody RoleChangeRequestDTO requestDTO,
            Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        RoleChangeResponseDTO createDTO = roleChangeRequestService.createRoleChangeRequest(memberId, requestDTO);
        URI location = URI.create("api/role-change-requests/" + createDTO.getRequestId());
        return ResponseEntity.created(location).body(createDTO);
    }

    @GetMapping
    public ResponseEntity<List<RoleChangeListDTO>> getAllRequest(Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        List<RoleChangeListDTO> response = roleChangeRequestService.getAllRequests(memberId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{requestId}")
    public ResponseEntity<RoleChangeResponseDTO> getRequestDetail(@PathVariable Long requestId,
            Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        RoleChangeResponseDTO responseDTO = roleChangeRequestService.getRequestDetail(requestId, memberId);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("{requestId}/approve")
    public ResponseEntity<String> approveRequest(@PathVariable Long requestId
            , Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        roleChangeRequestService.approveRequest(requestId, memberId);
        return ResponseEntity.ok("승인완료");
    }


    @GetMapping("/approved")
    public ResponseEntity<List<ApprovedRoleListDTO>> getApprovedRequests(Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        List<ApprovedRoleListDTO> approvedList = roleChangeRequestService.getApproveRequests(memberId);
        return ResponseEntity.ok(approvedList);
    }

    @PutMapping("/{requestId}/change-role")
    public ResponseEntity<String> changeMemberRole(
            @PathVariable Long requestId, @RequestBody RoleChangeRequestDTO requestDTO,
            Authentication authentication
    ) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        roleChangeRequestService.changeMemberRole(requestId, requestDTO, memberId);
        return ResponseEntity.ok("등급 재변경 완료");
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long requestId,
                                                Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        roleChangeRequestService.deleteRequest(requestId, memberId);
        return ResponseEntity.noContent().build();
    }

}
