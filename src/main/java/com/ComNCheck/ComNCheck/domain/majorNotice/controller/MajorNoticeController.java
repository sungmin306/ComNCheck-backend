package com.ComNCheck.ComNCheck.domain.majorNotice.controller;

import com.ComNCheck.ComNCheck.domain.majorNotice.model.dto.response.MajorNoticeResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorNotice.model.dto.response.PageMajorNoticeResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorNotice.service.MajorNoticeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/major/notices")
@RestController
public class MajorNoticeController {

    private final MajorNoticeService majorNoticeService;

    @GetMapping
    public ResponseEntity<List<MajorNoticeResponseDTO>> getAllMajorNotices() {
        List<MajorNoticeResponseDTO> lists = majorNoticeService.getAllMajorNotices();
        return ResponseEntity.ok(lists);
    }

    @GetMapping("/pages")
    public ResponseEntity<PageMajorNoticeResponseDTO> getMajorNoticesPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageMajorNoticeResponseDTO pageResponse = majorNoticeService.getMajorNoticesPage(page, size);
        return ResponseEntity.ok(pageResponse);
    }
}
