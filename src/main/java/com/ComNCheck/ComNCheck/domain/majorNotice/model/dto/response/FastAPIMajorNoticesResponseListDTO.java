package com.ComNCheck.ComNCheck.domain.majorNotice.model.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FastAPIMajorNoticesResponseListDTO {
    private List<MajorNoticeResponseDTO> notices;
}
