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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;
    private String location;
    private String notice;
    private String googleFormLink;

    private List<MultipartFile> cardNewsImages;

}
