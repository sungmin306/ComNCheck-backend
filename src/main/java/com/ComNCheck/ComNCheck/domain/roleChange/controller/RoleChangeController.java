package com.ComNCheck.ComNCheck.domain.roleChange.controller;

import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.request.RoleChangeRequestDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.ApprovedRoleListDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.RoleChangeListDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.model.dto.response.RoleChangeResponseDTO;
import com.ComNCheck.ComNCheck.domain.roleChange.service.RoleChangeRequestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
            @RequestBody RoleChangeRequestDTO requestDTO) {
        RoleChangeResponseDTO createDTO = roleChangeRequestService.createRoleChangeRequest(requestDTO);
        URI location = URI.create("api/role-change-requests/" + createDTO.getRequestId());
        return ResponseEntity.created(location).body(createDTO);
    }

    @PreAuthorize("hasRole('MAJOR_PRESIDENT')")
    @GetMapping
    public ResponseEntity<List<RoleChangeListDTO>> getAllRequest() {
        List<RoleChangeListDTO> response = roleChangeRequestService.getAllRequests();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('MAJOR_PRESIDENT')")
    @GetMapping("/{requestId}")
    public ResponseEntity<RoleChangeResponseDTO> getRequestDetail(@PathVariable Long requestId) {
        RoleChangeResponseDTO responseDTO = roleChangeRequestService.getRequestDetail(requestId);
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('MAJOR_PRESIDENT')")
    @PostMapping("{requestId}/approve")
    public ResponseEntity<String> approveRequest(@PathVariable Long requestId) {
        roleChangeRequestService.approveRequest(requestId);
        return ResponseEntity.ok("승인완료");
    }
    @PreAuthorize("hasRole('MAJOR_PRESIDENT')")
    @GetMapping("/approved")
    public ResponseEntity<List<ApprovedRoleListDTO>> getApprovedRequests() {
        List<ApprovedRoleListDTO> approvedList = roleChangeRequestService.getApproveRequests();
        return ResponseEntity.ok(approvedList);
    }

    @PreAuthorize("hasRole('MAJOR_PRESIDENT')")
    @PutMapping("/{requestId}/change-role")
    public ResponseEntity<String> changeMemberRole(
            @PathVariable Long requestId, @RequestBody RoleChangeRequestDTO requestDTO
    ) {
        roleChangeRequestService.changeMemberRole(requestId, requestDTO);
        return ResponseEntity.ok("등급 재변경 완료");
    }

    @PreAuthorize("hasRole('MAJOR_PRESIDENT')")
    @DeleteMapping("/{requestId}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long requestId) {
        roleChangeRequestService.deleteRequest(requestId);
        return ResponseEntity.noContent().build();
    }


}
