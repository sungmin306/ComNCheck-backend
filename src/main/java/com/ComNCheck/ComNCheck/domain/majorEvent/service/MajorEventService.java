package com.ComNCheck.ComNCheck.domain.majorEvent.service;

import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.request.EventCreateRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.request.EventUpdateRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.response.EventListResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.response.EventResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.response.PagedEventListResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.entity.MajorEvent;
import com.ComNCheck.ComNCheck.domain.majorEvent.repository.MajorEventRepository;
import com.ComNCheck.ComNCheck.domain.member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.member.repository.MemberRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MajorEventService {

    private final MajorEventRepository majorEventRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public EventResponseDTO createMajorEvent(EventCreateRequestDTO requestDTO, Long writerId) {
        Member writer = memberRepository.findByMemberId(writerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        List<String> imageUrls = uploadImagesToGcs(requestDTO.getCardNewsImages());

        MajorEvent majorEvent = MajorEvent.builder()
                .writer(writer)
                .eventName(requestDTO.getEventName())
                .date(requestDTO.getDate())
                .time(requestDTO.getTime())
                .location(requestDTO.getLocation())
                .notice(requestDTO.getNotice())
                .googleFormLink(requestDTO.getGoogleFormLink())
                .cardNewsImageUrls(imageUrls)
                .build();

        MajorEvent savedMajorEvent = majorEventRepository.save(majorEvent);
        return EventResponseDTO.of(savedMajorEvent);
    }

    public EventResponseDTO getMajorEvent(Long majorEventId) {
        MajorEvent majorEvent = majorEventRepository.findById(majorEventId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학부 행사 정보가 없습니다."));
        return EventResponseDTO.of(majorEvent);
    }

    public List<EventListResponseDTO> getAllMajorEventsNotPassed() {
        List<MajorEvent> all = majorEventRepository.findAll();

        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        List<MajorEvent> filtered = all.stream()
                .filter(e -> isNotPassed(e, today, currentTime))
                .collect(Collectors.toList());

        filtered.sort(Comparator.comparing(MajorEvent::getDate)
                        .thenComparing(MajorEvent::getTime));

        return filtered.stream()
                .map(EventListResponseDTO::of)
                .collect(Collectors.toList());
    }

//    public PagedEventListResponseDTO getAllMajorEventPage(int page, int size) {
//        List<MajorEvent> all = majorEventRepository.findAll();
//
//        LocalDate today = LocalDate.now();
//        LocalTime currentTime = LocalTime.now();
//
//        List<MajorEvent> filtered = all.stream()
//                .filter(e -> isNotPassed(e, today, currentTime))
//                .collect(Collectors.toList());
//
//        filtered.sort(Comparator.comparing(MajorEvent::getDate)
//                .thenComparing(MajorEvent::getTime));
//
//        long totalElements = filtered.size();
//        int totalPages = (int) Math.ceil((double) totalElements / size);
//
//        if (page < 1) {
//            page = 1;
//        } else if (page >= totalPages && totalPages > 0) {
//            page = totalPages -1;
//        }
//        int zeroBasedPage = page - 1;
//        int startIndex = zeroBasedPage * size;
//        int endIndex = Math.min(startIndex + size, (int) totalElements);
//
//        List<MajorEvent> pageList = (startIndex < endIndex)
//                ? filtered.subList(startIndex, endIndex)
//                : Collections.emptyList();
//
//        List<EventListResponseDTO> content = pageList.stream()
//                .map(EventListResponseDTO::of)
//                .toList();
//
//        return PagedEventListResponseDTO.builder()
//                .currentPage(page)
//                .totalPages(totalPages)
//                .totalElements(totalElements)
//                .size(size)
//                .content(content)
//                .build();
//
//    }

    @Transactional
    public EventResponseDTO updateMajorEvent(Long majorEventId, EventUpdateRequestDTO requestDTO) {
        MajorEvent majorEvent = majorEventRepository.findById(majorEventId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학부 행사 정보가 없습니다."));

        majorEvent.updateEvent(
                requestDTO.getEventName(),
                requestDTO.getDate(),
                requestDTO.getTime(),
                requestDTO.getLocation(),
                requestDTO.getNotice(),
                requestDTO.getGoogleFormLink()
        );
        if (requestDTO.getCardNewsImages() != null && !requestDTO.getCardNewsImages().isEmpty()) {
            List<String> newImageUrls = uploadImagesToGcs(requestDTO.getCardNewsImages());
            majorEvent.updateCardNewsImages(newImageUrls);
        }

        return EventResponseDTO.of(majorEvent);
    }

    @Transactional
    public void deleteMajorEvent(Long majorEventId) {
        MajorEvent majorEvent = majorEventRepository.findById(majorEventId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학부 행사 정보가 없습니다."));
        majorEventRepository.delete(majorEvent);
    }


    private List<String> uploadImagesToGcs(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> uploadUrls = new ArrayList<>();
        for(MultipartFile file : images) {
            // gcs 업로드 호출
            String url = "https://gcs.com" + file.getOriginalFilename();
            uploadUrls.add(url);
        }
        return uploadUrls;
    }

    private boolean isNotPassed(MajorEvent majorEvent, LocalDate today, LocalTime currentTime) {
        return majorEvent.getDate().isAfter(today)
                || (majorEvent.getDate().isEqual(today) && majorEvent.getTime().isBefore(currentTime));
    }
}
