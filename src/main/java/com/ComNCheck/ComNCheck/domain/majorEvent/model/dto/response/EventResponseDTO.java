package com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.response;

import com.ComNCheck.ComNCheck.domain.majorEvent.model.entity.MajorEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class EventResponseDTO {

    private Long id;
    private String eventName;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String notice;
    private String googleFormLink;
    private List<String> cardNewsImageUrls;

    public static EventResponseDTO of(MajorEvent majorEvent) {
        return EventResponseDTO.builder()
                .id(majorEvent.getMajorEventId())
                .eventName(majorEvent.getEventName())
                .date(majorEvent.getDate())
                .time(majorEvent.getTime())
                .location(majorEvent.getLocation())
                .notice(majorEvent.getNotice())
                .googleFormLink(majorEvent.getGoogleFormLink())
                .cardNewsImageUrls(majorEvent.getCardNewsImageUrls())
                .build();
    }
}
