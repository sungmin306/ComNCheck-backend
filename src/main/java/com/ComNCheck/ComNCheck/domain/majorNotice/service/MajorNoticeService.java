package com.ComNCheck.ComNCheck.domain.majorNotice.service;

import com.ComNCheck.ComNCheck.domain.global.infrastructure.FastApiClient;
import com.ComNCheck.ComNCheck.domain.majorNotice.model.dto.response.FastAPIMajorNoticesResponseListDTO;
import com.ComNCheck.ComNCheck.domain.majorNotice.model.dto.response.MajorNoticeResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorNotice.model.dto.response.PageMajorNoticeResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorNotice.model.entity.MajorNotice;
import com.ComNCheck.ComNCheck.domain.majorNotice.repository.MajorNoticeRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MajorNoticeService {

    private final FastApiClient fastApiClient;
    private final MajorNoticeRepository majorNoticeRepository;

    @Transactional
    public void syncMajorNotices() {
        FastAPIMajorNoticesResponseListDTO response = fastApiClient.fetchMajorNotices();

        List<MajorNotice> changeMajorNotices = new ArrayList<>();

        if(response != null && response.getNotices() != null) {
            for(MajorNoticeResponseDTO dto : response.getNotices()) {
                Optional<MajorNotice> findNotice = majorNoticeRepository.findByNoticeId(dto.getNoticeId());
                if(findNotice.isEmpty()) {
                    MajorNotice newMajorNotice = new MajorNotice(dto);
                    majorNoticeRepository.save(newMajorNotice);
                    changeMajorNotices.add(newMajorNotice);
                } else {
                    MajorNotice existingMajorNotice = findNotice.get();
                    if(!existingMajorNotice.equalsDTO(dto)) {
                        existingMajorNotice.updateFromDto(dto);
                        majorNoticeRepository.save(existingMajorNotice);
                        changeMajorNotices.add(existingMajorNotice);
                    }
                }
            }
        }
        if(!changeMajorNotices.isEmpty()) {
            // fcm 기능 구현
        }
    }

    public List<MajorNoticeResponseDTO> getAllMajorNotices() {
        return majorNoticeRepository.findAll()
                .stream()
                .map(MajorNoticeResponseDTO::of)
                .toList();
    }

    public PageMajorNoticeResponseDTO getMajorNoticesPage(int page, int size) {
        List<MajorNotice> allNotices = majorNoticeRepository.findAll();
        allNotices.sort(Comparator.comparing(MajorNotice::getDate).reversed());

        long totalElements = allNotices.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page < 1) {
            page = 1;
        } else if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }

        int zeroBasedPage = page - 1;
        int startIndex = zeroBasedPage * size;
        int endIndex = Math.min(startIndex + size, (int) totalElements);

        List<MajorNotice> pageList = (startIndex < endIndex)
                ? allNotices.subList(startIndex, endIndex)
                : Collections.emptyList();

        List<MajorNoticeResponseDTO> content = pageList.stream()
                .map(MajorNoticeResponseDTO::of)
                .collect(Collectors.toList());

        return PageMajorNoticeResponseDTO.builder()
                .currentPage(page)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .size(size)
                .content(content)
                .build();
    }
}
