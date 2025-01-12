package com.OhsunDosun.security.handler;

import com.OhsunDosun.member.dto.Member;
import com.OhsunDosun.security.utill.JsonResponse;
import com.OhsunDosun.security.utill.JwtProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProcessor jwtProcessor;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 인증 결과 Principal
        Member member = (Member) authentication.getPrincipal();
        String token = jwtProcessor.generateToken(member.getUsername());
        member.setToken(token);
        JsonResponse.send(response, member);
    }
}
