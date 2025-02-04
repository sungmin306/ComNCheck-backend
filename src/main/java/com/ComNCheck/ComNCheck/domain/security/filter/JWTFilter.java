package com.ComNCheck.ComNCheck.domain.security.filter;

import com.ComNCheck.ComNCheck.domain.member.model.dto.response.MemberDTO;
import com.ComNCheck.ComNCheck.domain.member.model.entity.Role;
import com.ComNCheck.ComNCheck.domain.security.exception.TokenExpiredException;
import com.ComNCheck.ComNCheck.domain.security.oauth.CustomOAuth2Member;
import com.ComNCheck.ComNCheck.domain.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    private static final String[] EXCLUDED_PATHS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/V3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/login/**",
            "/oauth2/**",
            "/h2-console/**",
            "api/v1/**"
    };
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        for (String excludedPath : EXCLUDED_PATHS) {
            if (pathMatcher.match(excludedPath, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) { // 쿠키 이름 확인
                    token = cookie.getValue();
                    logger.debug("Found Authorization cookie: " + token);
                    break;
                }
            }
        }

        try {
            if (token != null) {
                if (jwtUtil.isExpired(token)) {
                    throw new TokenExpiredException("만료된 토큰입니다.");
                }

                String username = jwtUtil.getUsername(token);
                Long id = jwtUtil.getId(token);
                Role role = jwtUtil.getRole(token);

                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setMemberId(id);
                memberDTO.setName(username);
                memberDTO.setRole(role);

                CustomOAuth2Member customOAuth2Member = new CustomOAuth2Member(memberDTO);

                Authentication authToken =
                        new UsernamePasswordAuthenticationToken(
                                customOAuth2Member,
                                null,
                                customOAuth2Member.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.debug("SecurityContext set with user: " + username);
            } else {
                logger.debug("No JWT token found in cookies.");
            }
        } catch (Exception e) {
            logger.error("Authentication error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
