package com.ComNCheck.ComNCheck.domain.member.model.dto.response;

public interface OAuth2Response {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}
