package com.ComNCheck.ComNCheck.domain.majorEvent.model.dto.request;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class EventCreateRequestDTO {

    private String eventName;
    private String date;
    private String time;
    private String location;
    private String notice;
    private String googleFormLink;

    private List<MultipartFile> cardNewsImages;

    public LocalDate getParsedDate() {
        return date != null ? LocalDate.parse(date) : null;
    }

    public LocalTime getParsedTime() {
        return time != null ? LocalTime.parse(time) : null;
    }

}
