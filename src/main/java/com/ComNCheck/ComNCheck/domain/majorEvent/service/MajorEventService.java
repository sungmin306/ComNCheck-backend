package com.ComNCheck.ComNCheck.domain.majorEvent.service;

import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.request.EventCreateRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.request.EventUpdateRequestDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.response.EventListResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.response.EventResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.response.PagedEventListResponseDTO;
import com.ComNCheck.ComNCheck.domain.majorEvent.model.entity.MajorEvent;
import com.ComNCheck.ComNCheck.domain.majorEvent.repository.MajorEventRepository;
import com.ComNCheck.ComNCheck.domain.member.model.entity.Member;
import com.ComNCheck.ComNCheck.domain.member.model.entity.Role;
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
        System.out.println("서비스 들어옴");
        Member writer = memberRepository.findByMemberId(writerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        isCheckRole(writer);

        List<String> imageUrls = uploadImagesToGcs(requestDTO.getCardNewsImages());

        LocalDate eventDate = requestDTO.getParsedDate();
        LocalTime eventTime = requestDTO.getParsedTime();
        MajorEvent majorEvent = MajorEvent.builder()
                .writer(writer)
                .eventName(requestDTO.getEventName())
                .date(eventDate)
                .time(eventTime)
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
        // 코드 상에서 정렬 보다는 디비에서 정렬하고 보내는 것이 더 효율적일꺼같음 추후 리펙토링 필요
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

    @Transactional
    public EventResponseDTO updateMajorEvent(Long majorEventId, EventUpdateRequestDTO requestDTO, Long memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 회원이 없습니다."));
        isCheckRole(member);

        MajorEvent majorEvent = majorEventRepository.findById(majorEventId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학부 행사 정보가 없습니다."));

        LocalDate eventDate = requestDTO.getParsedDate();
        LocalTime eventTime = requestDTO.getParsedTime();

        majorEvent.updateEvent(
                requestDTO.getEventName(),
                eventDate,
                eventTime,
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
    public void deleteMajorEvent(Long majorEventId, Long memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 회원이 없습니다."));
        isCheckRole(member);

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

    public void isCheckRole(Member member) {
        System.out.println("조건문 들어옴");
        Role checkRole = member.getRole();
        if(checkRole != Role.ROLE_ADMIN && checkRole != Role.ROLE_MAJOR_PRESIDENT && checkRole != Role.ROLE_STUDENT_COUNCIL) {
            System.out.println("접근 권한이 없음");
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }
}
