package com.OhsunDosun.security.fillter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.OhsunDosun.security.utill.JwtProcessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";   // 끝에 공백 있음

    private final JwtProcessor jwtProcessor;
    private final UserDetailsService userDetailsService;

    private Authentication getAuthentication(String token) {
        String username = jwtProcessor.getUsername(token);
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            String token = bearerToken.substring(BEARER_PREFIX.length());

            // 토큰 유효성 검사
            if (!jwtProcessor.validateToken(token)) {  // 토큰이 유효하지 않으면 예외를 던지기
                throw new ExpiredJwtException(null, null, "만료된 토큰입니다.");
            }

            // 토큰에서 사용자 정보 추출 및 Authentication 객체 구성 후 SecurityContext에 저장
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        super.doFilter(request, response, filterChain);
    }
}
