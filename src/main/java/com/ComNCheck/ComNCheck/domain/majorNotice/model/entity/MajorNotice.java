package com.ComNCheck.ComNCheck.domain.majorNotice.model.entity;

import com.ComNCheck.ComNCheck.domain.majorNotice.model.dto.response.MajorNoticeResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Table(name = "notices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MajorNotice {

    @Id
    @Column(name = "major_notice_id")
    private int noticeId;
\
    @Column
    private String title;

    @Column
    private LocalDate date;

    @Column
    private String link;

    public MajorNotice(MajorNoticeResponseDTO dto) {
        this.noticeId = dto.getNoticeId();
        this.title = dto.getTitle();
        this.date = dto.getDate();
        this.link = dto.getLink();
    }

    public boolean equalsDTO(MajorNoticeResponseDTO dto) {
        return this.noticeId== dto.getNoticeId() &&
                this.title.equals(dto.getTitle()) &&
                this.date.isEqual(dto.getDate()) &&
                this.link.equals(dto.getLink());
    }
    public void updateFromDto(MajorNoticeResponseDTO dto) {
        this.title = dto.getTitle();
        this.date = dto.getDate();
        this.link = dto.getLink();
    }

}
