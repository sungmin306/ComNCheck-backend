package com.ComNCheck.ComNCheck.domain.Member.controller;

import com.ComNCheck.ComNCheck.domain.Member.model.dto.request.RoleChangeRequestCreateDTO;
import com.ComNCheck.ComNCheck.domain.Member.model.dto.response.RoleChangeRequestResponseDTO;
import com.ComNCheck.ComNCheck.domain.Member.service.RoleChangeRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
@RequestMapping("/api/v1/role-change-requests")
@RequiredArgsConstructor
@RestController
public class RoleChangeController {

    private final RoleChangeRequestService roleChangeRequestService;

    @PostMapping
    public ResponseEntity<RoleChangeRequestResponseDTO> createRoleChangeRequest(
            @RequestBody RoleChangeRequestCreateDTO requestDTO) {
        RoleChangeRequestResponseDTO createDTO = roleChangeRequestService.createRoleChangeRequest(requestDTO);
        URI location = URI.create("api/role-change-requests/" + createDTO.getRequestId());
        return ResponseEntity.created(location).body(createDTO);
    }

//    @GetMapping
//    public ResponseEntity<List<RoleChangeRequestResponseDTO>>
}
