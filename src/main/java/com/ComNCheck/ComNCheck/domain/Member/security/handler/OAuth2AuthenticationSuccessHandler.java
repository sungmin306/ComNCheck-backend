package com.ComNCheck.ComNCheck.domain.Member.security.handler;
import com.ComNCheck.ComNCheck.domain.Member.model.dto.TokenDTO;
import com.ComNCheck.ComNCheck.domain.Member.util.TokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 사용자 이메일을 사용하여 JWT 생성
        String email = oAuth2User.getAttribute("email");
        // 여기서는 간단히 사용자 이메일을 Authentication 객체의 이름으로 사용
        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

        // 프론트엔드 리다이렉트 URL (실제 프론트엔드 URL로 변경)
        String redirectUri = "http://localhost:3000/oauth2/redirect";

        // JWT 토큰을 쿼리 파라미터로 추가
        String targetUrl = redirectUri + "?accessToken=" + tokenDTO.getAccessToken();

        try {
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
