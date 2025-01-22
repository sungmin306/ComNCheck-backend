package com.ComNCheck.ComNCheck.domain.Member.infrastructure;

import com.ComNCheck.ComNCheck.domain.Member.exception.FastApiException;
import com.ComNCheck.ComNCheck.domain.Member.model.dto.response.FastApiResponseDTO;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
@Component
@RequiredArgsConstructor
public class FastApiClient {
    private final RestTemplate restTemplate;

    private static final String FAST_API_URL= "http://localhost:8000/api/v1/compare-and-ocr/";

    public FastApiResponseDTO sendImage(MultipartFile imageFile) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            ByteArrayResource resource = new ByteArrayResource(imageFile.getBytes()) {
                @Override
                public String getFilename() {
                    return imageFile.getOriginalFilename();
                }
            };
            body.add("file", resource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<FastApiResponseDTO> response = restTemplate.postForEntity(
                    FAST_API_URL,
                    requestEntity,
                    FastApiResponseDTO.class
            );

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new FastApiException("FastAPI 호출 실패: " + response.getStatusCode());
            }

            return response.getBody();

        } catch (IOException e) {
            throw new FastApiException("이미지 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
