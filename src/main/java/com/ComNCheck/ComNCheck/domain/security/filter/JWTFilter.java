package com.ComNCheck.ComNCheck.domain.security.filter;

import com.ComNCheck.ComNCheck.domain.Member.model.dto.response.MemberDTO;
import com.ComNCheck.ComNCheck.domain.Member.model.entity.Role;
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
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        try {
            if (token == null) {
                throw new IllegalArgumentException("토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(token)) {
                throw new TokenExpiredException("만료된 토큰입니다.");
            }

            String username = jwtUtil.getUsername(token);
            Role role = jwtUtil.getRole(token);

            MemberDTO memberDTO = new MemberDTO();
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
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }
}
