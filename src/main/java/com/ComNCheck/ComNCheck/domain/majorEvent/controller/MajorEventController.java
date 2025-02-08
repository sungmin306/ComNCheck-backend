package com.ComNCheck.ComNCheck.domain.majorEvent.controller;

import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.request.EventCreateRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.request.EventUpdateRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.response.EventListResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.response.EventResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.response.PagedEventListResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.service.MajorEventService;
import com.ComNCheck.ComNCheck.domain.security.oauth.CustomOAuth2Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/major-event")
@RequiredArgsConstructor
@RestController
public class MajorEventController {

    private final MajorEventService majorEventService;

    @PostMapping
    public ResponseEntity<EventResponseDTO> createMajorEvent(@ModelAttribute EventCreateRequestDTO requestDTO,
                                                             Authentication authentication) {
        CustomOAuth2Member principal = (CustomOAuth2Member) authentication.getPrincipal();
        Long memberId = principal.getMemberDTO().getMemberId();
        EventResponseDTO responseDTO = majorEventService.createMajorEvent(requestDTO, memberId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{majorEventId}")
    public ResponseEntity<EventResponseDTO> getMajorEvent(@PathVariable Long majorEventId) {
        EventResponseDTO responseDTO = majorEventService.getMajorEvent(majorEventId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<EventListResponseDTO>> getAllMajorEventsNotPassed() {
        List<EventListResponseDTO> allMajorEventsNotPassed = majorEventService.getAllMajorEventsNotPassed();
        return ResponseEntity.ok(allMajorEventsNotPassed);
    }

    @GetMapping("/pages")
    public ResponseEntity<PagedEventListResponseDTO> getAllMajorEventPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedEventListResponseDTO responseDTO = majorEventService.getAllMajorEventPage(page, size);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{majorEventId}")
    public ResponseEntity<EventResponseDTO> updateMajorEvent(
            @PathVariable Long majorEventId,
            @ModelAttribute EventUpdateRequestDTO requestDTO
    ) {
        EventResponseDTO updateDTO = majorEventService.updateMajorEvent(majorEventId, requestDTO);
        return ResponseEntity.ok(updateDTO);
    }

    @DeleteMapping("/{majorEventId}")
    public ResponseEntity<Void> deleteMajorEvent(@PathVariable Long majorEventId) {
        majorEventService.deleteMajorEvent(majorEventId);
        return ResponseEntity.noContent().build();
    }

}
